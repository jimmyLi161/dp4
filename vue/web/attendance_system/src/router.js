import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  // mode: 'hash',
  // base: process.env.BASE_URL,
  routes: [
    {
      path:'/login',
      component: () => import('./views/Login.vue')
    },
    {
      path: '/',
      component: () => import('@/views/dashboard/Index'),
      meta: {
        require_login: true
      },
      children: [
        // Dashboard
        {
          name: 'Dashboard',
          path: 'home',
          component: () => import('@/views/dashboard/Dashboard'),
        },
        // Pages
        {
          name: 'Leave Post',
          path: 'pages/leave_post',
          component: () => import('@/views/dashboard/pages/LeavePost'),
        },
        {
          name: 'Leave List',
          path: 'pages/leave_list',
          component: () => import('@/views/dashboard/pages/LeaveList'),
        },
        {
          name: 'Leave Examination',
          path: 'pages/leave_examination',
          component: () => import('@/views/dashboard/pages/LeaveExamination')
        },
        {
          name: 'Approval List',
          path: 'pages/approval_list',
          component: () => import('@/views/dashboard/pages/ApprovalList')
        },
        {
          name: 'Employee Info',
          path: 'pages/employee_info',
          component: () => import('@/views/dashboard/pages/EmployeeInfo')
        },
        {
          name: 'Attendance',
          path: 'pages/attendance',
          component: () => import('@/views/dashboard/pages/Attendance')
        }
      ],
    },
  ],
})
