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
    <el-menu-item index="/StaffManage/Home">
        <i class="el-icon-house"></i>
        <span slot="title">首页</span>
    </el-menu-item>
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-reading"></i>
        <span slot="title">图书管理</span>
      </template>
      <el-menu-item index="/StaffManage/AddBook">图书信息入库</el-menu-item>
      <el-menu-item index="/StaffManage/BookList">图书查看</el-menu-item>
    </el-submenu>
    <el-menu-item index="/StaffManage/BookCirculation">
      <i class="el-icon-sort"></i>
      <span slot="title">图书流通</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-refresh"></i>
        <span slot="title">图书流入流出管理</span>
      </template>
      <el-menu-item index="/StaffManage/CirculationIn">图书流入管理</el-menu-item>
      <el-menu-item index="/StaffManage/CirculationOut">图书流出管理</el-menu-item>
    </el-submenu>
    <el-menu-item index="/StaffManage/BorrowOut">
      <i class="el-icon-s-order"></i>
      <span slot="title">图书借出归还管理</span>
    </el-menu-item>
    <el-menu-item index="/StaffManage/Analysis">
      <i class="el-icon-s-data"></i>
      <span slot="title">统计分析管理</span>
    </el-menu-item>
  </el-menu>
</template>

<style></style>