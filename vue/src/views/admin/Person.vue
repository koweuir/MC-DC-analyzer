<template>
  <el-card shadow="always" class="centered-form">
    <el-form label-width="80px" size="big">
      <el-upload
          class="avatar-uploader"
          :action="'http://localhost:9090/User/Upload'"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :on-success="handleAvatarSuccess">
        <el-image fit="cover" v-if="form.avatarPath" :src="form.avatarPath" class="avatar"></el-image>
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item class="formItem" label="用户名">
        <el-input v-model="form.userName" readonly ></el-input>
      </el-form-item>
      <el-form-item class="formItem" label="密码">
        <el-input show-password v-model="form.password" readonly ></el-input>
      </el-form-item>
      <el-form-item class="formItem" label="真实姓名">
        <el-input v-model="form.trueName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item class="formItem" label="性别">
        <el-select style="font-size: 16px" v-model="form.sex" placeholder="请选择用户性别" autocomplete="off">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
          <el-option label="保密" value="保密"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="formItem" label="联系电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item class="formItem" label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="单位名称">
        <el-select style="font-size: 16px" v-model="form.unitName" placeholder="请选择所属单位">
          <el-option
              v-for="(item,index) in options"
              :key="index"
              :label="item.unitName"
              :value="item.unitName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="formItem" label="地址">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center;">
        <el-button style="margin-right: 70px; font-size: 16px" type="primary" @click="save">保 存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>

export default {
  name: "Person",
  data() {
    return {
      form: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      options:[]
    }
  },
  created(){
    this.request.post("http://localhost:9090/Unit/GetAllUnits").then(res => {
      this.options = res;
    })
  },
  methods: {
    save() {
      this.request.post("http://localhost:9090/User/Update", this.form).then(res => {
        if (res !== "") {
          this.$message.success("用户信息保存成功")
          // 更新浏览器存储的用户信息
          localStorage.setItem("user", JSON.stringify(res))
        } else {
          this.$message.error("用户信息保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarPath = res
      this.request.post("http://localhost:9090/User/Update", this.form).then(res => {
        if (res !== "") {
          this.$message.success("用户头像设置成功")
          // 更新浏览器存储的用户信息
          localStorage.setItem("user", JSON.stringify(res))
        } else {
          this.$message.error("用户头像设置失败")
        }
      })
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt3M = file.size / 1024 / 1024 < 5;
      if (!isJPG && !isPNG) {
        this.$message.error("上传头像图片只能是 JPG 格式或 png 格式!");
      }
      if (!isLt3M) {
        this.$message.error("上传头像图片大小不能超过5MB!");
      }
      return (isJPG||isPNG) && isLt3M;
    },
  }
}
</script>

<style>
.formItem{
  width: 1000px;
}
.centered-form {
  display: flex;
  justify-content: center;
}
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>