import Vue from 'vue'
import Vuex from 'vuex'
import CreatePersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

const vuexPersisted = new CreatePersistedState({
  storage: window.sessionStorage
})

export default new Vuex.Store({
  state: {
    username: '', // 历史遗留问题：实质上是员工id
    password: '',
    auth: '',
    emp_name: 'TestUser',
    department_id: '',
    department: '',
    usertype: -1,
    entry_date: '',
    birthday: '',
    token: '',
    gender: '',
    auth_str: ['普通员工', '部门经理', '总经理', '管理员'],
    dept_str: ['', '市场部', '财务部', '人事部'],
    barColor: 'rgba(0, 0, 0, .8), rgba(0, 0, 0, .8)',
    barImage: 'https://demos.creative-tim.com/material-dashboard/assets/img/sidebar-1.jpg',
    drawer: null,
  },
  mutations: {
    SET_BAR_IMAGE (state, payload) {
      state.barImage = payload
    },
    SET_DRAWER (state, payload) {
      state.drawer = payload
    },
    SET_USER_NAME(state, username) {
      state.username = username;
    },
    SET_PASSWORD(state, password) {
      state.password = password;
    },
    SET_AUTH(state, auth) {
      state.auth = auth;
    },
    SET_USER_TYPE(state, user_type) {
      state.usertype = user_type;
    },
    SET_TOKEN(state, token) {
      state.token = token;
    },
    SET_EMP_NAME(state, emp_name) {
      state.emp_name = emp_name;
    },
    SET_DEPT(state, dept_name) {
      state.department = dept_name;
    },
    SET_DEPT_ID(state, dept_id) {
      state.department_id = dept_id;
    },
    SET_ENTRY_DAY(state, entry_date) {
      state.entry_date = entry_date;
    },
    SET_BIRTHDAY(state, birthday) {
      state.birthday = birthday;
    },
    SET_GENDER(state, gender) {
      state.gender = gender;
    }
  },
  actions: {

  },
  plugins: [vuexPersisted]
})
