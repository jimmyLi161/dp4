<!--
 * @FileDescription: 打卡记录导入和工资扣取列表获取
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
        <base-material-card icon="mdi-format-list-checks" title="考勤信息相关操作" class="px-4 py-3" color="primary">
            <v-tabs v-model="tabs">
                <v-tab key="import">导入打卡记录</v-tab>
                <v-tab key="withdrew">导出请假情况</v-tab>
            </v-tabs>

            <v-tabs-items v-model="tabs" v-if="$store.state.department_id == 3">
                <v-tab-item key="import">
                    <v-card flat>
                        <v-card-text>
                            <v-file-input label="添加打卡记录文件" outlined v-model="file">
                            </v-file-input>
                        </v-card-text>
                        <v-card-actions class="ma-n5 px-10">
                            <v-btn color="primary dark" @click="import_file">导入</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-tab-item>
                <v-tab-item key="withdraw">
                    <v-card flat>
                        <v-card-text>
                            <v-btn color="primary dark" @click="withdraw_file">获取</v-btn>
                        </v-card-text>
                    </v-card>
                </v-tab-item>
            </v-tabs-items>
            <v-row v-else justify="center">
                <p class="display-1 font-weight-light my-8">无权限查看</p>
            </v-row>
        </base-material-card>
    </v-container>
</template>

<script>
export default {
  name: 'Attendance',
  data: () => ({
    tabs: 0,
    file: undefined,
    // 操作提示框
    snackbar_visible: false,
    snackbar_color: 'success',
    operation_info: '',
  }),
  methods: {
    import_file() {
      let that = this;
      let attendance_file = new FormData();
      attendance_file.append('file', this.file);
      fetch('http://localhost:9095/AttendanceSystem/attendance', {
        method: 'POST',
        body: attendance_file,
        headers: new Headers({
          'username': that.$store.state.username,
          'Token': that.$store.state.token,
        })
      }).then(res => {
        if (res.ok) {
          that.snackbar_color = 'success';
          that.operation_info = '记录已导入';
          that.snackbar_visible = true;
        }
        else {
          that.snackbar_color = 'error';
          that.operation_info = res.status + ' - 导入出现错误';
          that.snackbar_visible = true;
        }
      })
    },
    withdraw_file() {
      let that = this;
      fetch('http://localhost:9095/AttendanceSystem/employee/report', {
        method: 'GET',
        responseType: 'blob',
        headers: new Headers({
          'username': that.$store.state.username,
          'Token': that.$store.state.token,
          'Content-Type': 'application/json'
        })
      }).then(res => res.blob())
      .then(blob => {
        let item = new Blob([blob], {type: 'application/vnd.ms-excel'});
        let file_name = 'report.xlsx';
        var link = document.createElement('a');
        link.style.display = 'none';
        link.href = window.URL.createObjectURL(blob);
        link.download = file_name;
        link.click();
        link.href = window.URL.createObjectURL(blob);
      })
    }
  }
}
</script>