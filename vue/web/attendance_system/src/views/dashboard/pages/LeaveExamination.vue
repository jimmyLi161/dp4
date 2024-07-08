<!--
 * @FileDescription: 申请审批（仅对部门经理以上级别开放）
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
        <base-material-card icon="mdi-file-question-outline" title="待审申请" class="px-4 py-3" color="primary">
            <v-data-table :headers="headers" :items="applications_to_examine" item-key="appID" show-select
                v-model="selected" v-if="$store.state.auth > 0">
                <template v-slot:top>
                    <v-toolbar flat>
                        <v-toolbar-title class="display-1 font-weight-light">
                            可选操作
                        </v-toolbar-title>
                        <v-divider class="mx-4" inset vertical></v-divider>
                        <v-spacer></v-spacer>
                        <v-dialog v-model="dialog_approve" max-width="500px">
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn color="primary" dark class="mb-2 mx-2" v-bind="attrs" v-on="on" x-small fab>
                                    <v-icon>mdi-check</v-icon>
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>批准申请</v-card-title>
                                <v-card-actions>
                                    <v-container>
                                        <v-row justify="center">
                                            <v-col cols="6" style="text-align: center">
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="approve_items = []; dialog_approve = false;">
                                                    CANCEL
                                                </v-btn>
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="confirmApprove">
                                                    OK
                                                </v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-dialog v-model="dialog_reject" max-width="500px">
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn color="error" dark class="mb-2" v-bind="attrs" v-on="on" x-small fab>
                                    <v-icon>mdi-close</v-icon>
                                </v-btn>
                            </template>
                            <v-card>
                                <v-card-title>拒绝申请</v-card-title>
                                <v-card-text>
                                    <v-container>
                                        <v-row>
                                            <v-col cols="12">
                                                <v-textarea class="purple-input" prepend-icon="mdi-comment"
                                                    v-model="comment" />
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-text>
                                <v-card-actions>
                                    <v-container>
                                        <v-row justify="center">
                                            <v-col cols="6" style="text-align: center">
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="reject_items = []; dialog_reject = false;">
                                                    CANCEL
                                                </v-btn>
                                                <v-btn text color="primary" style="text-align: center;"
                                                    @click="confirmReject">
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
                <template v-slot:[`item.type`]="{ item }">
                    {{leave_types[item.type - 1]}}
                </template>
                <template v-slot:[`item.state`]="{ item }">
                    {{application_state[item.state]}}
                </template>
            </v-data-table>
            <v-row v-else justify="center">
                <p class="display-1 font-weight-light my-8">无权限查看</p>
            </v-row>
        </base-material-card>
    </v-container>
</template>

<script>
export default {
  data: () => ({
    headers: [
      {text: '申请编号', value: 'appID'},
      {text: '申请人编号', value: 'empID'},
      {text: '申请人', value: 'empName'},
      {text: '开始时间', value: 'startTime'},
      {text: '结束时间', value: 'endTime'},
      {text: '类型', value: 'type'},
      {text: '申请理由', value: 'reason'},
      {text: '申请状态', value: 'state'},
    ],
    application_state: ['已拒绝', '待部门经理批准', '待总经理批准', '已通过'],
    leave_types: ['年假', '私人事假', '病假', '因公外出', '其它'],
    applications_to_examine: [],
    selected: [],
    dialog_approve: false,
    approve_items: [],
    dialog_reject: false,
    reject_items: [],
    comment: '请输入审批意见。',
    // 操作提示框
    snackbar_visible: false,
    snackbar_color: 'success',
    operation_info: ''
  }),
  methods: {
    update_list(vm) {
      vm.applications_to_examine = [];
      fetch('http://localhost:9095/AttendanceSystem/leave', {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': vm.$store.state.username,
          'Token': vm.$store.state.token
        })
      }).then(res => res.json())
      .then(json => {
        json.forEach((item) => {
          if (item.state == vm.$store.state.auth) {
            vm.applications_to_examine.push(item);
          }
        })
      });
    },
    doPost(item, is_agree) {
      let that = this;
      let approval_to_post = {
        Approval: {
          empID: this.$store.state.username,
          time: new Date().toISOString().substring(0, 10),
          appID: item.appID,
          comment: is_agree? '' : this.comment,
          type: 1,
          approval: is_agree
        }
      };
      fetch('http://localhost:9095/AttendanceSystem/approval', {
        method: 'POST',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        }),
        body: JSON.stringify(approval_to_post)
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = '审批意见已提交';
          that.update_list(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status + ' - 提交错误';
          that.update_list(that);
          that.snackbar_visible = true;
        }
      })
    },
    confirmApprove() {
      let that = this;
      this.approve_items = this.selected;
      this.approve_items.forEach(item => {
        that.doPost(item, true);
      });
      this.approve_items = [];
      this.dialog_approve = false;
    },
    confirmReject() {
      let that = this;
      this.reject_items = this.selected;
      this.reject_items.forEach(item => {
        that.doPost(item, false);
      });
      this.reject_items = [];
      this.dialog_reject = false;
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.update_list(vm)
    })
  }
}
</script>