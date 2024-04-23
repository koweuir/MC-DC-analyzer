import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: ()=> import('../views/Login.vue'),
  },
  {
    path: '/AdminManage',
    name: 'AdminManage',
    component: ()=> import('../views/admin/Manage.vue'),
    redirect: "/AdminManage/Home",
    children: [
      { path: 'Home', name: 'Home', component: () => import('../views/Home.vue'), },
      { path: 'Person', name: 'Person', component: () => import('../views/admin/Person.vue'), },
      { path: 'Book', name: 'Book', component: () => import('../views/admin/Book.vue'), },
      { path: 'AddUser', name: 'AddUser', component: () => import('../views/admin/AddUser.vue'), },
      { path: 'UserList', name: 'UserList', component: () => import('../views/admin/UserList.vue'), },
      { path: 'AddUnit', name: 'AddUnit', component: () => import('../views/admin/AddUnit.vue'), },
      { path: 'UnitList', name: 'UnitList', component: () => import('../views/admin/UnitList.vue'), },
    ]
  },
  {
    path: '/StaffManage',
    name: 'StaffManage',
    component: ()=> import('../views/staff/Manage.vue'),
    redirect: "/StaffManage/Home",
    children: [
      { path: 'Home', name: 'Home', component: () => import('../views/Home.vue'), },
      { path: 'Person', name: 'Person', component: () => import('../views/staff/Person.vue'), },
      { path: 'AddBook', name: 'AddBook', component: () => import('../views/staff/AddBook.vue'), },
      { path: 'BookList', name: 'BookList', component: () => import('../views/staff/BookList.vue'), },
      { path: 'BookCirculation', name: 'BookCirculation', component: () => import('../views/staff/BookCirculation.vue'), },
      { path: 'CirculationIn', name: 'CirculationIn', component: () => import('../views/staff/CirculationIn.vue'), },
      { path: 'CirculationOut', name: 'CirculationOut', component: () => import('../views/staff/CirculationOut.vue'), },
      { path: 'BorrowOut', name: 'BorrowOut', component: () => import('../views/staff/BorrowOut.vue'), },
      { path: 'Analysis', name: 'Analysis', component: () => import('../views/staff/Analysis.vue'), },
    ]
  },
  {
    path: '/Register',
    name: 'Register',
    component: ()=> import('../views/Register.vue'),
  },
  {
    path: '/ResetPassword',
    name: 'ResetPassword',
    component: ()=> import('../views/ResetPassword.vue'),
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
