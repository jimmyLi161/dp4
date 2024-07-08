<!--
 * @FileDescription: 审批列表
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
        <v-dialog v-model="dialog_edit" max-width="500px">
            <v-card>
                <v-card-title>编辑审批意见</v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row v-if="operable">
                            <v-col cols="12" sm="6" md="4">
                                <v-select v-model="decision" :items="decisions" item-text="type" item-value="value"
                                    prepend-icon="mdi-magnify" label="审批决定" persistent-hint return-object
                                    two-line></v-select>
                            </v-col>
                            <v-col cols="12">
                                <v-textarea v-if="!decision.value" class="purple-input" prepend-icon="mdi-comment"
                                    v-model="comment" />
                            </v-col>
                        </v-row>
                        <v-banner v-else two-line class="font-weight-light" transition="slide-y-transition">
                            <v-avatar slot="icon" color="primary" size="30">
                                <v-icon color="white" icon="mdi-hand-right">mdi-hand-right</v-icon>
                            </v-avatar>
                            上级已提交审批意见，无法修改<br />
                            或申请已完成全部流程，无法修改
                        </v-banner>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-btn text color="primary" @click="edit_item = {}; dialog_edit = false;">
                        CANCEL
                    </v-btn>
                    <v-btn text color="primary" @click="confirmEdit">
                        OK
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-dialog v-model="dialog_delete" max-width="500px">
            <v-card>
                <v-card-title>删除审批意见</v-card-title>
                <v-card-text>
                    <v-container></v-container>
                </v-card-text>
                <v-card-actions>
                    <v-btn text color="primary" @click="delete_item = {}; dialog_delete = false;">
                        CANCEL
                    </v-btn>
                    <v-btn text color="primary" @click="confirmDelete">
                        OK
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <base-material-card icon="mdi-clipboard-text-search-outline" title="审批意见列表" class="px-4 py-3" color="primary">
            <v-data-table :headers="headers" :items="approval_list" item-key="appID" v-if="$store.state.auth > 0">
                <template v-slot:[`item.approval`]="{ item }">
                    <v-chip :color="getSlotColor(item.approval)" dark>
                        {{item.approval? '同意' : '拒绝'}}
                    </v-chip>
                </template>
                <template v-slot:[`item.comment`]="{ item }">
                    {{item.approval? '(同意时无需填写)' : item.comment}}
                </template>
                <template v-slot:[`item.actions`]="{ item }">
                    <v-icon small class="mr-2" @click="editItem(item)">
                        mdi-pencil
                    </v-icon>
                    <v-icon small @click="deleteItem(item)">
                        mdi-delete
                    </v-icon>
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
    // 页面
    headers: [
      {text: '审批人编号', value: 'empID'},
      {text: '审批时间', value: 'time'},
      {text: '被审批申请编号', value: 'appID'},
      {text: '审批决定', value: 'approval'},
      {text: '审批意见', value: 'comment'},
      {text: '操作', value: 'actions'}
    ],
    approval_list: [],
    // 操作模态框
    operable: true,
    // 编辑
    dialog_edit: false,
    edit_item: {},
    decision: {type: '', value: null},
    decisions: [
      {type: '同意', value: true},
      {type: '拒绝', value: false}
    ],
    comment: '在此填入审批意见。',
    // 删除
    dialog_delete: false,
    delete_item: {},
    // 操作提示框
    snackbar_visible: false,
    snackbar_color: 'success',
    operation_info: ''
  }),
  methods: {
    update_approval_list(vm) {
      vm.approval_list = [];
      fetch('http://localhost:9095/AttendanceSystem/approval/id?empID=' + vm.$store.state.username, {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': vm.$store.state.username,
          'Token': vm.$store.state.token
        })
      }).then(res => res.json())
      .then(json => {
        vm.approval_list = json;
      });
    },
    getSlotColor(approval) {
      if (approval) {
        return 'success'
      }
      else {
        return 'error'
      }
    },
    confirmEdit() {
      if (!this.operable) {
        this.dialog_edit = false;
        return;
      }
      let that = this;
      let approval_to_edit = {
        Approval: {
          appID: this.edit_item.appID,
          empID: this.edit_item.empID,
          type: this.$store.state.auth,
          approval: this.decision.value,
          comment: this.decision.value? '' : this.comment
        }
      };
      fetch('http://localhost:9095/AttendanceSystem/approval', {
        method: 'PUT',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        }),
        body: JSON.stringify(approval_to_edit)
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = '修改已保存';
          that.update_approval_list(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status == 403? '权限不足' : res.status + '修改出现错误';
          that.update_approval_list(that);
          that.snackbar_visible = true;
        }
      });
      this.dialog_edit = false;
    },
    editItem(item) {
      let that = this;
      fetch('http://localhost:9095/AttendanceSystem/leave/id?appID=' + item.appID, {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        }),
      }).then(res => res.json())
      .then(json => {
        if (json[0].state == 3) {
          that.operable = false;
        }
        else {
          that.operable = true;
        }
      });
      this.decision = (item.approval? this.decisions[0] : this.decisions[1]);
      this.comment = (item.approval? "在此填入审批意见。" : item.comment);
      this.edit_item = item;
      this.dialog_edit = true;
    },
    confirmDelete() {
      let that = this;
      fetch('http://localhost:9095/AttendanceSystem/approval' + '?empID=' + that.delete_item.empID + '&appID=' + that.delete_item.appID, {
        method: 'DELETE',
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.$store.state.username,
          'Token': that.$store.state.token
        }),
      }).then(res => {
        if (res.status == 200) {
          that.snackbar_color = 'success';
          that.operation_info = '删除成功';
          that.update_approval_list(that);
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status == 403? '权限不足' : res.status + '删除出现错误';
          that.update_approval_list(that);
          that.snackbar_visible = true;
        }
      });
      this.dialog_delete = false;
    },
    deleteItem(item) {
      this.delete_item = item;
      this.dialog_delete = true;
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.update_approval_list(vm);
    })
  }
}
</script>