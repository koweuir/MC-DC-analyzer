<script>
export default {
  name: "Header",
  props: {
    co1lapseBtnClass: String,
    collapse: Function,
    pathName: String
  },
  created(){
    this.load()
  },
  data(){
    return {
      user: {}
    }
  },
  methods:{
    logout() {
      // 获取当前时间戳
      const currentTime = new Date().toLocaleString();
      // 更新 user 对象中的时间戳信息
      this.user = JSON.parse(localStorage.getItem("user"))
      if (this.user) {
        this.user.lastLoginTime = currentTime;
      }
      // 从 localStorage 中移除 user
      localStorage.removeItem('user');
      // 发送更新请求
      this.request.post("http://localhost:9090/User/Update", this.user)
      // 跳转到首页
      this.$router.push("/");
      // 提示退出成功
      this.$message.success("退出成功");
    },
    load(){
      this.user = JSON.parse(localStorage.getItem("user"));
    }
  }
}
</script>

<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span :class="co1lapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
    </div>
    <el-avatar style="margin-top: 10px" fit="cover" :src="this.user.avatarPath"></el-avatar>
    <el-dropdown style="width: 75px; cursor: pointer; text-align: right">
      <span><b>{{ this.user.userName }}</b></span><i class="el-icon-arrow-down" style="margin-left: 5px "></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
          <router-link to="/AdminManage/Person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <router-link to="/ResetPassword">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
  color: inherit;  /* 继承父级元素的文本颜色 */
  font-weight: inherit;  /* 继承父级元素的字体粗细 */
  font-style: inherit;  /* 继承父级元素的字体样式 */
}

.el-menu--collapse span {
  visibility: hidden;
}
</style>