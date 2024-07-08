<template>
    <v-container id="leave-post" fluid tag="section">
        <v-row>
            <v-col cols="12">
                <base-material-card class="v-card-profile" avatar="OIP-C.jpg">
                    <v-card-text class="text-center">
                        <h6 class="display-1 mb-1 grey--text font-weight-light">
                            {{ $store.state.dept_str[$store.state.department_id] }}
                            {{ $store.state.auth_str[$store.state.auth] }}
                        </h6>

                        <h4 class="display-2 font-weight-light mb-3 black--text">
                            {{ $store.state.emp_name }}
                        </h4>
                        <v-divider></v-divider>
                        <v-row class="d-flex justify-space-around align-center" style="height: auto;">
                            <v-col cols="4">
                                <v-list-item>
                                    <v-list-item-content>
                                        <v-list-item-title>{{ holidays_info.yearHolidays }}</v-list-item-title>
                                        <v-list-item-subtitle>剩余年假</v-list-item-subtitle>
                                    </v-list-item-content>
                                </v-list-item>
                            </v-col>
                            <v-col cols="4">
                                <v-list-item>
                                    <v-list-item-content>
                                        <v-list-item-title>{{ holidays_info.sickHolidays }}</v-list-item-title>
                                        <v-list-item-subtitle>剩余带薪病假</v-list-item-subtitle>
                                    </v-list-item-content>
                                </v-list-item>
                            </v-col>
                            <v-col cols="4">
                                <v-list-item>
                                    <v-list-item-content>
                                        <v-list-item-title>{{ holidays_info.privateHolidays }}</v-list-item-title>
                                        <v-list-item-subtitle>已用事假</v-list-item-subtitle>
                                    </v-list-item-content>
                                </v-list-item>
                            </v-col>
                        </v-row>
                    </v-card-text>
                </base-material-card>
            </v-col>

            <v-col cols="12">
                <base-material-card color="primary">
                    <template v-slot:heading>
                        <div class="display-2 font-weight-light">
                            申请填写
                        </div>
                        <div class="subtitle-1 font-weight-light">
                            请完成以下信息
                        </div>
                    </template>

                    <v-form>
                        <v-container class="py-0">
                            <v-row>
                                <v-col cols="12" md="6">
                                    <v-select v-model="leave_type" :items="leave_types" item-text="type"
                                        item-value="value" prepend-icon="mdi-animation" label="请假类型" persistent-hint
                                        return-object single-line></v-select>
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-menu v-model="start_menu" :close-on-content-click="false" :nudge-right="40"
                                        transition="scale-transition" offset-y>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-text-field v-model="start_date" label="开始日期" prepend-icon="mdi-calendar"
                                                readonly v-bind="attrs" v-on="on">
                                            </v-text-field>
                                        </template>
                                        <v-date-picker v-model="start_date" @input="start_menu = false" no-title
                                            color="primary"></v-date-picker>
                                    </v-menu>
                                </v-col>

                                <v-col cols="12" md="6">
                                    <v-menu v-model="end_menu" :close-on-content-click="false" :nudge-right="40"
                                        transition="scale-transition" offset-y>
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-text-field v-model="end_date" label="结束日期" prepend-icon="mdi-calendar"
                                                readonly v-bind="attrs" v-on="on">
                                            </v-text-field>
                                        </template>
                                        <v-date-picker v-model="end_date" @input="end_menu = false" no-title
                                            color="primary"></v-date-picker>
                                    </v-menu>
                                </v-col>

                                <v-col cols="12">
                                    <v-textarea class="purple-input" prepend-icon="mdi-comment" v-model="reason"
                                        label="请假理由" placeholder="在此填入请假理由。如果选择其他请详细说明理由"></v-textarea>
                                </v-col>

                                <v-col cols="12" class="text-right">
                                    <v-btn color="primary" class="mr-0" @click="submit_app">
                                        提交
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-form>
                </base-material-card>
            </v-col>
        </v-row>

        <base-material-snackbar v-model="snackbar_visible" :type="snackbar_color" v-bind="{
                                        'bottom': true,
                                        'right': true
                                    }" style="margin-top: -2.5rem">
            {{ operation_info }}
        </base-material-snackbar>
    </v-container>
</template>

<script>
export default {
    data: () => ({
        // 信息卡片相关
        holidays_info: [],
        // 申请表单相关
        leave_type: { type: '', value: 0 },
        leave_types: [
            { type: '年假', value: 1 },
            { type: '私人事假', value: 2 },
            { type: '病假', value: 3 },
            { type: '因公外出', value: 4 },
            { type: '其它', value: 5 }
        ],
        start_menu: false,
        end_menu: false,
        start_date: new Date().toISOString().substring(0, 10),
        end_date: new Date().toISOString().substring(0, 10),
        reason: '',
        // 操作提示框
        snackbar_visible: false,
        snackbar_color: 'success',
        operation_info: ''
    }),
    methods: {
        submit_app() {
            let that = this;
            let application_data = {
                Leave: {
                    appID: (parseInt(localStorage.getItem("leave_cnt")) + 1),
                    empID: parseInt(this.$store.state.username),
                    empName: this.$store.state.emp_name,
                    empDeptID: this.$store.state.department_id,
                    empDeptName: this.$store.state.department,
                    reason: this.reason,
                    type: this.leave_type.value,
                    state: "1",
                    startTime: this.start_date,
                    endTime: this.end_date
                }
            };
            fetch('http://localhost:9095/AttendanceSystem/leave', {
                method: 'POST',
                body: JSON.stringify(application_data),
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'username': (that.$store.state.username),
                    'Token': (that.$store.state.token)
                })
            }).then(res => {
                if (res.status == 200) {
                    that.snackbar_color = 'success';
                    that.operation_info = '申请提交成功'
                    that.updateHolidaysInfo(that);
                    that.snackbar_visible = true;
                    let leave_cnt = localStorage.getItem("leave_cnt");
                    ++leave_cnt;
                    localStorage.setItem("leave_cnt", leave_cnt);
                }
                else {
                    that.snackbar_color = 'error';
                    that.operation_info = res.status + ' - 提交出现错误';
                    that.updateHolidaysInfo(that);
                    that.snackbar_visible = true;
                }
            });
            this.start_date = new Date().toISOString().substring(0, 10);
            this.end_date = new Date().toISOString().substring(0, 10);
            this.leave_type = { type: '', value: 0 };
            this.reason = '在此填入请假理由。若选择了选项其它，请在理由处详细说明（如产假/陪产假等）。';
        },
        updateHolidaysInfo(vm) {
            fetch('http://localhost:9095/AttendanceSystem/holidays/id' + '?empID=' + vm.$store.state.username, {
                method: 'GET',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'username': vm.$store.state.username,
                    'Token': vm.$store.state.token
                })
            }).then(res => res.json())
                .then(json => {
                    vm.holidays_info = json;
                });
        }
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            vm.updateHolidaysInfo(vm);
        })
    }
}
</script>
