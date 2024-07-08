<!--
 * @FileDescription: 登陆界面
 * @Author: 毕凯斌
 -->

<template>
    <div>
        <v-app style="background-image: url('R-C.jpg'); background-size: cover;">
            <div class="pa-0" style="top: 43%; bottom: 45%; right: 8%; left: 50%; position: absolute;">
                <v-card class="mx-auto elevation-5 text-center pa-5" max-width="550"
                    style="border-radius:10px;position:relative;top:-100px;">
                    <v-card-subtitle class="display-2 black--text pa-1">
                        登录
                    </v-card-subtitle>
                    <v-card-text>
                        <v-text-field v-model="username" :rules="id_rules" color="grey darken-4" label="ID" required
                            class="ma-n1 px-5">
                        </v-text-field>
                        <v-text-field v-model="password" :rules="password_rules" color="grey darken-4" label="Password"
                            :type="password_visible? 'text': 'password'"
                            :append-icon="password_visible? 'mdi-eye' : 'mdi-eye-off'" required class="ma-n1 px-5"
                            @click:append="password_visible = !password_visible"
                            @keyup.enter.native="submit_login_form()">
                        </v-text-field>
                    </v-card-text>
                    <v-card-actions>
                        <v-btn color="grey darken-4" dark block rounded @click="submit_login_form()">LOGIN</v-btn>
                    </v-card-actions>
                </v-card>
            </div>
        </v-app>
    </div>
</template>

<script>
import bcrypt from 'bcryptjs'
export default {
  name: 'Login',
  data() {
    return {
      password_visible: false,
      username: '',
      password: '',
      id_rules: [
        id => !!id || 'ID is required.'
      ],
      password_rules: [
        pwd => !!pwd || 'Password is required.'
      ]
    }
  },
  methods: {
    submit_login_form() {
      let that = this;
      let salt = bcrypt.genSaltSync(10);
      let hash = bcrypt.hashSync(this.password, salt);
      this.$store.commit("SET_USER_NAME", this.username);
      this.$store.commit("SET_PASSWORD", this.password);
      let post_data = {
        username: this.username,
        password: hash
      }
      fetch('http://localhost:9095/login', {
        method: 'POST',
        body: JSON.stringify(post_data),
        headers: new Headers({
          'Content-Type': 'application/json',
          'username': that.username
        }),
        mode: 'cors'
      }).then(res => {return res.json();})
      .then(json => {
        if (json.loginStatus == 'success') {
          sessionStorage.setItem('token', json.Token);
          sessionStorage.setItem('username', that.username);
          that.$store.commit("SET_TOKEN", json.Token);
          that.$store.commit("SET_USER_NAME", that.username);
          fetch('http://localhost:9095/AttendanceSystem/employee/id' + '?empID=' + that.username, {
            method: 'GET',
            headers: new Headers({
              'Content-Type': 'application/json',
              'username': that.username,
              'Token': that.$store.state.token
            })
          }).then(res => {return res.json()})
          .then(json => {
            that.$store.commit("SET_EMP_NAME", json.empName);
            that.$store.commit("SET_DEPT", json.deptName);
            that.$store.commit("SET_DEPT_ID", json.deptID);
            that.$store.commit("SET_AUTH", json.auth);
            that.$store.commit("SET_GENDER", json.gender);
            that.$store.commit("SET_BIRTHDAY", json.birthday);
            that.$store.commit("SET_ENTRY_DAY", json.getJobDay);
            that.$router.push('/home');
          });
        }
      }).catch(err => {
        console.log('请求错误', err);
      });
    }
  },
  created() {
    let leave_cnt = localStorage.getItem("leave_cnt");
    if (leave_cnt == null) {
            localStorage.setItem("leave_cnt", 0)
            console.log()
    }
  }
}
</script>