<script>

export default{
  name: "AddUser",
  created(){
    this.request.post("http://localhost:9090/Unit/GetAllUnits").then(res => {
      this.options = res;
    })
  },
  data() {
    return{
      user:{},
      options: [],
      rules:{
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        characterName: [
          { required: true, message: '请选择角色名', trigger: 'blur' },
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],
        unitName: [
          { required: true, message: '请选择所属单位', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    add(){
      this.request.post("http://localhost:9090/User/AddUser", this.user).then(res => {
        if (res === 2) {
          this.$message.warning("用户名已存在")
        } else if(res === 1){
          this.$message.success("用户添加成功")
        }else{
          this.$message.error("用户添加失败")
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <el-card shadow="always" >
      <el-form :model="user" label-width="100px" :rules="rules">
        <el-form-item label="用户名" prop="userName">
          <el-input style="font-size: 16px" v-model="user.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input style="font-size: 16px" v-model="user.password" show-password autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户角色" prop="characterName">
          <el-select style="font-size: 16px" v-model="user.characterName" placeholder="请选择用户角色" autocomplete="off">
            <el-option label="管理员" value="管理员"></el-option>
            <el-option label="工作人员" value="工作人员"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input style="font-size: 16px" v-model="user.trueName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select style="font-size: 16px" v-model="user.sex" placeholder="请选择用户性别" autocomplete="off">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
            <el-option label="保密" value="保密"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话">
          <el-input style="font-size: 16px" v-model="user.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input style="font-size: 16px" v-model="user.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input style="font-size: 16px" v-model="user.address" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-select style="font-size: 16px" v-model="user.unitName" placeholder="请选择用户单位" autocomplete="off">
            <el-option
                v-for="(item,index) in options"
                :key="index"
                :label="item.unitName"
                :value="item.unitName">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer" style="text-align: center; margin-top: 10px">
      <el-button type="primary" @click="add">添 加</el-button>
    </div>
  </div>
</template>

<style scoped>
</style>