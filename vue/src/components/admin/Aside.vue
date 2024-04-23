<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
}
</script>

<template>
  <el-menu style="min-height: 100%; overflow-x: hidden;"
           background-color="rgb(67,65,90)" text-color="#fff" active-text-color="#ffd04b"
           :collapse-transition="false" class="e1-menu-vertical-demo"
           :collapse="isCollapse"
            router>
    <div style="height:60px;line-height: 60px; text-align: center;">
      <img src="../../assets/logo.png" style="width: 40px; position: relative; top: 5px; margin-right: 1px;">
      <b style="color: bisque;" v-show="logoTextShow">图书管理系统</b>
    </div>
    <el-menu-item index="/AdminManage/Home">
        <i class="el-icon-house"></i>
        <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-user"></i>
        <span slot="title">用户管理</span>
      </template>
      <el-menu-item index="/AdminManage/UserList">用户列表</el-menu-item>
      <el-menu-item index="/AdminManage/AddUser">用户添加</el-menu-item>
    </el-submenu>
    <el-menu-item index="/AdminManage/Book">
      <i class="el-icon-reading"></i>
      <span slot="title">图书管理</span>
    </el-menu-item>
    <el-submenu index="3">
      <template slot="title"><i class="el-icon-office-building"></i>
        <span slot="title">单位管理</span>
      </template>
      <el-menu-item index="/AdminManage/AddUnit">新建单位</el-menu-item>
      <el-menu-item index="/AdminManage/UnitList">单位信息维护</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<style scoped>

</style>