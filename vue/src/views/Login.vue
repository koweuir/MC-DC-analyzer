<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: rgba(255, 255, 255, 0.8); width: 350px; height: 280px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px;color: rgba(0, 0, 0, 1)"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="userName">
          <el-input size="medium" prefix-icon="el-icon-user"  v-model="user.userName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password" @keyup.enter.native="Login"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-link href="/ResetPassword" :underline="false" style="margin-right: 125px" type="warning">忘记密码</el-link>
          <el-button type="primary" size="small" autocomplete="off" @click="Login" >登录</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        userName: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    Login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("http://localhost:9090/User/Login", this.user).then(res => {
            if (res!=="") {
              localStorage.setItem("user", JSON.stringify(res))  // 存储用户信息到浏览器
              if (res.characterName ==="管理员") {
                this.$message.success("管理员，登录成功，欢迎您，"+ this.user.userName + "!")
                this.$router.push("/AdminManage")
              } else if(res.characterName ==="读者") {
                if(res.isRegistration !== "是"){
                  this.$message.warning("首次登录，请先完善用户信息")
                  this.$router.push("/Person")
                }
                else{
                  this.$message.success("读者，登录成功，欢迎您，"+ this.user.userName + "!")
                  this.$router.push("/ReaderManage")
                }
              }else{
                this.$message.success("工作人员，登录成功，欢迎您，"+ this.user.userName + "!")
                this.$router.push("/StaffManage")
              }
            } else {
              this.$message.error("登录失败:用户名或密码错误！！！")
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  overflow: hidden;
  background-image: url('../assets/back.jpg'); /* 设置网页背景图片 */
  background-size: cover;
}
</style>