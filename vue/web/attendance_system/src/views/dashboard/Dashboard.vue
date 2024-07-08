<template>
    <v-container id="user-profile" fluid tag="section">
        <base-material-snackbar v-model="snackbar_visible" :type="snackbar_color" v-bind="{
            'bottom': true,
            'right': true
        }" style="margin-top: -2.5rem">
            {{ operation_info }}
        </base-material-snackbar>
        <v-row>
            <v-col cols="12" md="8" offset-md="2">
                <base-material-card class="v-card-profile" avatar="OIP-C.jpg" color="primary">
                    <v-card-text class="text-center">
                        <h4 class="display-2 font-weight-light mb-3 black--text">
                            {{ $store.state.emp_name }}
                        </h4>
                        <h6 class="display-1 mb-1 grey--text font-weight-light">
                            {{ $store.state.dept_str[$store.state.department_id] }}
                            {{ $store.state.auth_str[$store.state.auth] }}
                        </h6>
                    </v-card-text>
                </base-material-card>

                <base-material-card icon="mdi-account" title="个人信息" class="px-4 py-3 mt-4" color="primary">
                    <v-form>
                        <v-container class="py-0">
                            <v-row>
                                <v-col cols="12" md="6">
                                    <v-text-field label="员工编号" :value="$store.state.username" disabled />
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-text-field class="blue-input" label="所属部门"
                                        :value="$store.state.dept_str[$store.state.department_id]" disabled />
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-text-field label="部门编号" class="blue-input" v-model="deptID" disabled />
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-select v-model="gender" :items="gender_types" item-text="type_CN"
                                        item-value="value" label="员工性别" persistent-hint return-object single-line
                                        color="blue lighten-4"></v-select>
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-text-field label="入职日期" class="blue-input" :value="$store.state.entry_date"
                                        disabled />
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-menu v-model="birthday_menu" :close-on-content-click="false" :nudge-right="40"
                                        transition="scale-transition" offset-y>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-text-field v-model="birthday" label="员工生日" readonly v-bind="attrs"
                                                v-on="on">
                                            </v-text-field>
                                        </template>
                                        <v-date-picker header-color="transparent" v-model="birthday"
                                            @input="birthday_menu = false" no-title
                                            color="blue lighten-4"></v-date-picker>
                                    </v-menu>
                                </v-col>

                                <v-col cols="12" class="text-right">
                                    <v-btn color="primary" class="mr-0" @click="update_profile">
                                        更新信息
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-form>
                </base-material-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        gender_types: [
            { value: 1, type_CN: '男性', type_EN: 'male' },
            { value: 2, type_CN: '女性', type_EN: 'female' }
        ],
        gender: { value: 0, type_CN: '', type_EN: '' },
        birthday_menu: false,
        birthday: '1990-01-01',
        // 操作提示框
        snackbar_visible: false,
        snackbar_color: 'primary',
        operation_info: '',
        deptID: '',
        deptName: '',
    }),
    methods: {
        update_profile() {
            let that = this;
            let put_profile = {
                Employee: {
                    empID: that.$store.state.username,
                    gender: that.gender.type_EN,
                    birthday: that.birthday,
                    deptID: that.deptID,
                    deptName: that.deptName
                }
            }
            console.log(JSON.stringify(put_profile));
            fetch('http://localhost:9095/AttendanceSystem/employee', {
                method: 'PUT',
                headers: new Headers({
                    'username': that.$store.state.username,
                    'Token': that.$store.state.token,
                    'Content-Type': 'application/json'
                }),
                body: JSON.stringify(put_profile)
            }).then(res => {
                if (res.ok) {
                    that.snackbar_color = 'primary';
                    that.operation_info = '修改成功';
                    that.snackbar_visible = true;
                }
                else {
                    that.snackbar_color = 'error';
                    that.operation_info = res.status + ' - 修改失败';
                    that.snackbar_visible = true;
                }
            })
        }
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            vm.deptID = vm.$store.state.department_id
            vm.deptName = vm.$store.state.department
            vm.birthday = vm.$store.state.birthday
            if (vm.$store.state.gender == 'male') {
                vm.gender = vm.gender_types[0];
            }
            else {
                vm.gender = vm.gender_types[1];
            }
        })
    }
}
</script>
