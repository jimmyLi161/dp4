<!--
 * @FileDescription: 申请列表
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
        <base-material-card icon="mdi-clipboard-text" title="申请列表" class="px-4 py-3" color="primary">
            <v-tabs v-model="tabs">
                <v-tab key="individual">个人申请</v-tab>
                <v-tab key="department">部门申请</v-tab>
                <v-tab key="employees">全体员工申请</v-tab>
            </v-tabs>

            <v-tabs-items v-model="tabs">
                <v-tab-item key="individual">
                    <v-data-table :headers="headers.concat(actions_arr)" :items="applications_individual"
                        item-key="appID" show-select v-model="selected">
                        <template v-slot:top>
                            <v-toolbar flat>
                                <v-toolbar-title class="display-1 font-weight-light">批量操作</v-toolbar-title>
                                <v-divider class="mx-4" inset vertical></v-divider>
                                <v-spacer></v-spacer>
                                <v-dialog v-model="dialog_delete" max-width="500px">
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on" x-small fab>
                                            <v-icon @click="delete_items = selected">mdi-playlist-remove</v-icon>
                                        </v-btn>
                                    </template>
                                    <v-card>
                                        <v-card-title>
                                            确认删除选中的申请吗？
                                        </v-card-title>
                                        <v-card-text>
                                            <v-row justify="center">
                                                <v-col cols="6">
                                                    <v-btn text color="primary"
                                                        @click="delete_items = []; dialog_delete = false;">
                                                        CANCEL
                                                    </v-btn>
                                                    <v-btn text color="primary" @click="confirmDelete">
                                                        OK
                                                    </v-btn>
                                                </v-col>
                                            </v-row>
                                        </v-card-text>
                                    </v-card>
                                </v-dialog>
                                <v-dialog v-model="dialog_edit" max-width="600px">
                                    <v-card>
                                        <v-card-title>
                                            编辑申请
                                        </v-card-title>
                                        <v-card-text>
                                            <v-container>
                                                <v-row>
                                                    <v-col cols="12" sm="6" md="4">
                                                        <v-select v-model="leave_type" :items="leave_types"
                                                            item-text="type" item-value="value"
                                                            prepend-icon="mdi-animation" label="请假类型" persistent-hint
                                                            return-object single-line></v-select>
                                                    </v-col>
                                                    <v-col cols="12" sm="6" md="4">
                                                        <v-menu v-model="start_menu" :close-on-content-click="false"
                                                            :nudge-right="40" transition="scale-transition"
                                                            :offset-y="true">
                                                            <template v-slot:activator="{ on, attrs }">
                                                                <v-text-field v-model="start_date" label="开始日期"
                                                                    prepend-icon="mdi-calendar" readonly v-bind="attrs"
                                                                    v-on="on">
                                                                </v-text-field>
                                                            </template>
                                                            <v-date-picker v-model="start_date"
                                                                @input="start_menu = false" no-title
                                                                color="primary"></v-date-picker>
                                                        </v-menu>
                                                    </v-col>
                                                    <v-col cols="12" sm="6" md="4">
                                                        <v-menu v-model="end_menu" :close-on-content-click="false"
                                                            :nudge-right="40" transition="scale-transition" offset-y>
                                                            <template v-slot:activator="{ on, attrs }">
                                                                <v-text-field v-model="end_date" label="结束日期"
                                                                    prepend-icon="mdi-calendar" readonly v-bind="attrs"
                                                                    v-on="on">
                                                                </v-text-field>
                                                            </template>
                                                            <v-date-picker header-color="transparent" v-model="end_date"
                                                                @input="end_menu = false" no-title
                                                                color="primary"></v-date-picker>
                                                        </v-menu>
                                                    </v-col>
                                                    <v-col cols="12">
                                                        <v-textarea class="purple-input" prepend-icon="mdi-comment"
                                                            v-model="reason" />
                                                    </v-col>
                                                </v-row>
                                            </v-container>
                                        </v-card-text>
                                        <v-card-actions>
                                            <v-btn text color="primary"
                                                @click="edit_item = {}; dialog_edit = false;">
                                                CANCEL
                                            </v-btn>
                                            <v-btn text color="primary" @click="confirmEdit">
                                                OK
                                            </v-btn>
                                        </v-card-actions>
                                    </v-card>
                                </v-dialog>
                            </v-toolbar>
                            <v-banner v-model="banner_visible" single-line class="font-weight-light"
                                transition="slide-y-transition">
                                <v-avatar slot="icon" color="primary" size="30">
                                    <v-icon color="white" icon="mdi-hand-right">
                                        mdi-hand-right
                                    </v-icon>
                                </v-avatar>
                                已通过/拒绝的申请无法进行操作
                            </v-banner>
                        </template>
                        <template v-slot:[`item.type`]="{ item }">
                            {{leave_types[item.type - 1].type}}
                        </template>
                        <template v-slot:[`item.state`]="{ item }">
                            {{application_state[item.state]}}
                        </template>
                        <template v-slot:[`item.actions`]="{ item }">
                            <v-icon small class="mr-2" @click="editItem(item)"
                                v-if="item.state != 3 && item.state != 0">
                                mdi-pencil
                            </v-icon>
                            <v-icon small @click="deleteItem(item)" v-if="item.state != 3 && item.state != 0">
                                mdi-delete
                            </v-icon>
                        </template>

                    </v-data-table>
                </v-tab-item>

                <v-tab-item key="department">
                    <v-data-table :headers="info_arr.concat(headers)" :items="applications_department"
                        :search="search_departments" v-if="$store.state.auth >= 1">
                        <template v-slot:top>
                            <v-toolbar flat>
                                <v-toolbar-title>
                                    查询
                                </v-toolbar-title>
                                <v-divider class="mx-4" inset vertical></v-divider>
                                <v-spacer></v-spacer>
                                <v-text-field v-model="search_departments" append-icon="mdi-magnify" label="请输入查询条件"
                                    single-line hide-details></v-text-field>
                            </v-toolbar>
                        </template>
                        <template v-slot:[`item.type`]="{ item }">
                            {{leave_types[item.type - 1].type}}
                        </template>
                        <template v-slot:[`item.state`]="{ item }">
                            {{application_state[item.state]}}
                        </template>
                    </v-data-table>
                    <v-row v-else justify="center">
                        <p class="display-1 font-weight-light my-8">无权限查看</p>
                    </v-row>
                </v-tab-item>

                <v-tab-item key="employees">
                    <v-data-table :headers="info_arr.concat(headers)" :items="applications_employees"
                        :search="search_employees" v-if="$store.state.auth == 2">
                        <template v-slot:top>
                            <v-toolbar flat>
                                <v-toolbar-title>
                                    查询
                                </v-toolbar-title>
                                <v-divider class="mx-4" inset vertical></v-divider>
                                <v-spacer></v-spacer>
                                <v-text-field v-model="search_employees" append-icon="mdi-magnify" label="请输入查询条件"
                                    single-line hide-details></v-text-field>
                            </v-toolbar>
                        </template>
                        <template v-slot:[`item.type`]="{ item }">
                            {{leave_types[item.type - 1].type}}
                        </template>
                        <template v-slot:[`item.state`]="{ item }">
                            {{application_state[item.state]}}
                        </template>
                    </v-data-table>
                    <v-row v-else justify="center">
                        <p class="display-1 font-weight-light my-8">无权限查看</p>
                    </v-row>
                </v-tab-item>

            </v-tabs-items>
        </base-material-card>
        <div class="py-3" />
    </v-container>
</template>

<script>
export default {
  data: () => ({
    // 页面
    tabs: 0,
    headers: [
      {text: '申请编号', value: 'appID'},
      {text: '开始时间', value: 'startTime'},
      {text: '结束时间', value: 'endTime'},
      {text: '类型', value: 'type'},
      {text: '申请理由', value: 'reason'},
      {text: '申请状态', value: 'state'},
    ],
    actions_arr: [{text: '操作', value: 'actions', sortable: false}],
    info_arr: [
      {text: '申请人编号', value: 'empID'},
      {text: '申请人', value: 'empName'},
    ],
    applications_individual: [],
    applications_department: [],
    applications_employees: [],
    search: '',
    selected: [],
    application_state: ['已拒绝', '待部门经理批准', '待总经理批准', '已通过'],
    // 一些提示
    banner_visible: true,
    // 申请编辑相关
    dialog_edit: false,
    edit_item: {},
    leave_type: {type: '', value: 0},
    leave_types: [
      {type: '年假', value: 1},
      {type: '私人事假', value: 2},
      {type: '病假', value: 3},
      {type: '因公外出', value: 4},
      {type: '其它', value: 5}
    ],
    start_menu: false,
    end_menu: false,
    start_date: new Date().toISOString().substring(0, 10),
    end_date: new Date().toISOString().substring(0, 10),
    reason: '',
    // 搜索
    search_departments: '',
    search_employees: '',
    // 申请删除相关
    dialog_delete: false,
    delete_items: [],
    delete_bunch: false, 
    // 操作提示框
    snackbar_visible: false,
    snackbar_color: 'success',
    operation_info: '',
  }),
  methods: {
    update_individual(vm) {
      fetch('http://localhost:9095/AttendanceSystem/leave' + (vm.$store.state.auth > 0? '/id?empID=' + vm.$store.state.username : ''), {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': vm.$store.state.username,
          'Token': vm.$store.state.token
        })
      }).then(res => res.json())
      .then(json => {vm.applications_individual = json});
    },
    update_overall(vm) {
      fetch('http://localhost:9095/AttendanceSystem/leave', {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': vm.$store.state.username,
          'Token': vm.$store.state.token
        })
      }).then(res => res.json())
      .then(json => {
        if (vm.$store.state.auth == 1) {
          vm.applications_department = json;
        }
        else if (vm.$store.state.auth == 2) {
          vm.applications_employees = json;
        }
      })
    },
    // 编辑申请
    editItem(item) {
      this.edit_item = item;
      this.leave_type = this.leave_types[item.type-1];
      this.start_date = item.startTime;
      this.end_date = item.endTime;
      this.reason = item.reason;
      this.dialog_edit = true;
    },
    confirmEdit() {
      let that = this;
      this.edit_item.type = this.leave_type.value;
      this.edit_item.startTime = this.start_date;
      this.edit_item.endTime = this.end_date;
      this.edit_item.reason = this.reason;
      let modified_info = {Leave: this.edit_item};
      fetch('http://localhost:9095/AttendanceSystem/leave', {
        method: 'PUT',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        }),
        body: JSON.stringify(modified_info)
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = '修改已保存';
          that.update_individual(that);
          that.update_overall(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status + ' - 修改出现错误';
          that.update_individual(that);
          that.update_overall(that);
          that.snackbar_visible = true;
        }
      });
      this.edit_item = {};
      this.dialog_edit = false;
    },
    // 删除申请
    deleteItem(item) {
      this.dialog_delete = true;
      this.delete_items = [item];
    },
    doDelete(item) {
      let that = this;
      fetch('http://localhost:9095/AttendanceSystem/leave' + "?appID=" + item.appID, {
        method: 'DELETE',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        })
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = that.delete_bunch? '已完成批量删除' : '已删除请假申请' + item.appID;
          that.update_individual(that);
          that.update_overall(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status + ' - 删除出现错误';
          that.update_individual(that);
          that.update_overall(that);
          that.snackbar_visible = true;
        }
      })
    },
    confirmDelete() {
      this.delete_bunch = (this.delete_items.length > 1);
      this.delete_items.forEach(this.doDelete);
      this.dialog_delete = false;
    },
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.update_individual(vm);
      vm.update_overall(vm);
    });
  }
}
</script>
