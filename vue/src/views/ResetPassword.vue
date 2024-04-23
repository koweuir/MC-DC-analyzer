<template>
  <div class="wrapper">
    <div style="margin: 150px auto; background-color: rgba(255, 255, 255, 0.8); width: 350px; height: 450px; padding: 20px; border-radius: 10px">
      <div v-if="user.userName" style="margin: 20px 0; text-align: center; font-size: 24px"><b>修 改 密 码</b></div>
      <div v-else style="margin: 20px 0; text-align: center; font-size: 24px"><b>重 置 密 码</b></div>
      <el-form :model="verification" :rules="rules" ref="verificationCodeForm">
        <el-form-item prop="userName">
          <el-input v-if="user.userName" size="medium" prefix-icon="el-icon-user" :value="user.userName" :disabled="true"></el-input>
          <el-input v-else  placeholder="请输入用户名" size="medium" prefix-icon="el-icon-user" v-model="verification.userName"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input placeholder="请输入邮箱" size="medium" prefix-icon="el-icon-message" v-model="verification.email"></el-input>
        </el-form-item>
        <el-form-item prop="verificationCode">
          <el-input style="width: 210px" placeholder="请输入验证码" size="medium" prefix-icon="el-icon-finished" v-model="verification.verificationCode"></el-input>
          <el-button style="font-size: 12px; margin-left: 5px" size="small" type="primary" round @click="Send">发送验证码</el-button>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入新密码" size="medium" prefix-icon="el-icon-lock" show-password v-model="verification.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认新密码" size="medium" prefix-icon="el-icon-lock" show-password v-model="verification.confirmPassword" @keyup.enter.native="Reset"></el-input>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="Reset">重置</el-button>
          <el-button v-if="user.userName" type="warning" size="small" autocomplete="off" @click="back">返回</el-button>
          <el-button v-else type="warning" size="small"  autocomplete="off" @click="$router.push('/')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "ResetPassword",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      verification: {},
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ],
        verificationCode: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认新密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    Send(){
      if(!/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(this.verification.email)) {
        this.$message.warning("请输入正确的邮箱账号")
        return
      }
      this.request.get("http://localhost:9090/Email/SendVerificationCode", {
        params: {
          userName: this.verification.userName,
          email: this.verification.email
        }
      }).then(res => {
        if(res === 1){
          this.$message.success("发送成功！")
        }
        else if(res === 2){
          this.$message.warning("验证码未过期，请注意查收！")
        }
        else{
          this.$message.error("发送失败！")
        }
      })
    },
    Reset() {
      this.$refs['verificationCodeForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.verification.password !== this.verification.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("http://localhost:9090/User/Reset", this.verification).then(res => {
            if(res === 1) {
              this.$message.success("重置密码成功")
              this.$router.push('/')
            } else if(res === 2){
              this.$message.warning("验证码错误")
            } else{
              this.$message.error("重置密码失败")
            }
          })
        }
      });
    },
    back(){
      if(this.user.characterName === "管理员"){
        this.$router.push('/AdminManage')
      }else if(this.user.characterName === "读者"){
        this.$router.push('/ReaderManage')
      }else{
        this.$router.push('/StaffManage')
      }
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  overflow: hidden;
  background-image: url('../assets/back.jpg'); /* 设置网页背景图片 */
  background-size: cover;
}
</style>