<!--
 * @FileDescription: 全体员工信息（仅对人事部部门经理以上级别开放）
 * @Author: 毕凯斌
 -->
<template>
    <v-container fluid tag="section">
        <base-material-snackbar v-model="snackbar_visible" :type="snackbar_color" v-bind="{
        'bottom': true,
        'right': true
      }" style="margin-top: -2.5rem">
            {{operation_info}}
        </base-material-snackbar>
        <base-material-card icon="mdi-account" title="员工信息" class="px-4 py-3" color="primary">
            <v-data-table :headers="headers" :items="employee_info" item-key="empID" show-select v-model="selected">
                <template v-slot:top v-if="$store.state.department_id == 3">
                    <v-toolbar flat>
                        <v-toolbar-title>可选操作</v-toolbar-title>
                        <v-divider class="mx-4" inset vertical></v-divider>
                        <v-spacer></v-spacer>
                        <v-dialog v-model="dialog_plus" max-width="500px">
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn color="primary" dark class="mb-2 mx-2" v-bind="attrs" v-on="on" x-small fab>
                                    <v-icon>mdi-plus</v-icon>
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>添加员工信息</v-card-title>
                                <v-card-text>
                                    <v-container>
                                        <v-row>
                                            <v-col cols="12" md="4">
                                                <v-text-field label="员工编号" v-model="emp_id" class="purple-input" />
                                            </v-col>
                                            <v-col cols="12" md="4">
                                                <v-text-field label="员工姓名" v-model="emp_name" class="purple-input" />
                                            </v-col>
                                            <v-col cols="12" md="4">
                                                <v-select v-model="emp_dept" :items="depts" item-text="name_CN"
                                                    item-value="id" label="所属部门" persistent-hint return-object
                                                    single-line></v-select>
                                            </v-col>
                                            <v-col cols="12" md="4">
                                                <v-select v-model="auth" :items="auth_types" item-text="type"
                                                    item-value="value" label="职务" persistent-hint return-object
                                                    single-line></v-select>
                                            </v-col>
                                            <v-col cols="12" md="4">
                                                <v-select v-model="gender" :items="gender_types" item-text="type_CN"
                                                    item-value="value" label="员工性别" persistent-hint return-object
                                                    single-line></v-select>
                                            </v-col>
                                            <v-col cols="12" md="6">
                                                <v-menu v-model="birthday_menu" :close-on-content-click="false"
                                                    :nudge-right="40" transition="scale-transition" offset-y>
                                                    <template v-slot:activator="{ on, attrs }">
                                                        <v-text-field v-model="birthday" label="员工生日" readonly
                                                            v-bind="attrs" v-on="on">
                                                        </v-text-field>
                                                    </template>
                                                    <v-date-picker header-color="transparent" v-model="birthday"
                                                        @input="birthday_menu = false" no-title
                                                        color="primary"></v-date-picker>
                                                </v-menu>
                                            </v-col>
                                            <v-col cols="12" md="6">
                                                <v-menu v-model="entry_day_menu" :close-on-content-click="false"
                                                    :nudge-right="40" transition="scale-transition" offset-y>
                                                    <template v-slot:activator="{ on, attrs }">
                                                        <v-text-field v-model="entry_day" label="入职日期" readonly
                                                            v-bind="attrs" v-on="on">
                                                        </v-text-field>
                                                    </template>
                                                    <v-date-picker header-color="transparent" v-model="entry_day"
                                                        @input="entry_day_menu = false" no-title
                                                        color="primary"></v-date-picker>
                                                </v-menu>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-text>
                                <v-card-actions>
                                    <v-container>
                                        <v-row justify="center">
                                            <v-col cols="6" style="text-align: center">
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="clear_form(); dialog_plus = false;">
                                                    CANCEL
                                                </v-btn>
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="confirmPlus">
                                                    OK
                                                </v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-dialog v-model="dialog_minus" max-width="500px">
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn color="error" dark class="mb-2" v-bind="attrs" v-on="on" x-small fab>
                                    <v-icon>mdi-minus</v-icon>
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>删除员工信息</v-card-title>
                                <v-card-actions>
                                    <v-container>
                                        <v-row justify="center">
                                            <v-col cols="6" style="text-align: center">
                                                <v-btn text color="primary" style="text-align: center;">
                                                    CANCEL
                                                </v-btn>
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="confirmMinus">
                                                    OK
                                                </v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-toolbar>
                </template>
                <template v-slot:[`item.gender`]="{ item }">
                    {{getGender(item.gender)}}
                </template>
                <template v-slot:[`item.auth`]="{ item }">
                    {{$store.state.auth_str[item.auth]}}
                </template>
                <template v-slot:[`item.deptID`]="{ item }">
                    {{$store.state.dept_str[item.deptID]}}
                </template>
            </v-data-table>
        </base-material-card>
    </v-container>
</template>

<script>
export default {
  data: () => ({
    headers: [
      {text: '员工编号', value: 'empID'},
      {text: '员工姓名', value: 'empName'},
      {text: '员工部门', value: 'deptID'},
      {text: '职务', value: 'auth'},
      {text: '入职日期', value: 'getJobDay'},
      {text: '性别', value: 'gender'},
      {text: '生日', value: 'birthday'}
    ],
    employee_info: [],
    selected: [],
    // 操作模态框
    dialog_plus: false,
    dialog_minus: false,
    // 添加员工信息
    emp_id: null,
    emp_name: '',
    depts: [
      {id: 1, name_CN: '市场部', name_EN: 'market'},
      {id: 2, name_CN: '财务部', name_EN: 'financial'},
      {id: 3, name_CN: '人事部', name_EN: 'executive'},
    ],
    emp_dept: {id: 0, name_CN: '', name_EN: ''},
    auth_types: [
      {value: 0, type: '普通员工'},
      {value: 1, type: '部门经理'},
      {value: 2, type: '总经理'}
    ],
    auth: {value: -1, type: ''},
    gender_types: [
      {value: 1, type_CN: '男性', type_EN: 'male'},
      {value: 2, type_CN: '女性', type_EN: 'female'}
    ],
    gender: {value: 0, type_CN: '', type_EN: ''},
    birthday_menu: false,
    entry_day_menu: false,
    birthday: '1990-01-01',
    entry_day: '2023-03-01',
    // 删除员工信息
    minus_items: [],
    // 操作提示框
    snackbar_visible: false,
    snackbar_color: 'success',
    operation_info: ''
  }),
  methods: {
    update_employee_info(vm) {
      vm.employee_info = [];
      fetch('http://localhost:9095/AttendanceSystem/employee', {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': vm.$store.state.username,
          'Token': vm.$store.state.token
        })
      }).then(res => res.json())
      .then(json => {
        vm.employee_info = json;
      })
    },
    clear_form() {
      this.emp_id =  null;
      this.emp_name = '';
      this.emp_dept = {id: 0, name_CN: '', name_EN: ''}
      this.auth = {value: -1, type: ''};
      this.birthday = '1990-01-01';
      this.entry_day = '2023-03-01';
      this.gender = {value: 0, type_CN: '', type_EN: ''};
    },
    confirmPlus() {
      let that = this;
      let employee_plus = {
        Employee: {
          empID: that.emp_id,
          empName: that.emp_name,
          birthday: that.birthday,
          getJobDay: that.entry_day,
          gender: that.gender.type_EN,
          deptID: that.emp_dept.id,
          deptName: that.emp_dept.name_EN,
          auth: that.auth.value
        }
      };
      fetch('http://localhost:9095/AttendanceSystem/employee', {
        method: 'POST',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        }),
        body: JSON.stringify(employee_plus)
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = '添加成功';
          that.update_employee_info(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status + ' - 添加错误';
          that.update_employee_info(that);
          that.snackbar_visible = true;
        }
      });
      this.clear_form();
      this.dialog_plus = false;
    },
    doMinus(item) {
      let that = this;
      fetch('http://localhost:9095/AttendanceSystem/employee?empID=' + item.empID, {
        method: 'DELETE',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        })
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = '已完成删除';
          that.update_employee_info(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status + ' - 删除错误';
          that.update_employee_info(that);
          that.snackbar_visible = true;
        }
      })
    },
    confirmMinus() {
      let that = this;
      this.minus_items = this.selected;
      this.minus_items.forEach(item => {
        that.doMinus(item);
      });
      this.minus_items = [];
      this.dialog_minus = false;
    },
    getGender(gender) {
      if (gender == 'male') {
        return '男性';
      }
      else {
        return '女性'
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => vm.update_employee_info(vm))
  }
}
</script>