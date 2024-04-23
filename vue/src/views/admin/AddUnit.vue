<script>
export default{
  name: "AddUnit",
  data() {
    return{
      Unit:{},
      rules:{
        unitName: [
          { required: true, message: '请输入单位名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        contactPerson: [
          { required: true, message: '请输入联系人', trigger: 'blur' },
        ],
        contactNumber: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        emailAddress: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
        ],
        contactAddress: [
          { required: true, message: '请输入联系地址', trigger: 'blur' },
        ],
      }
    }
  },
  methods: {
    add(){
      this.request.post("http://localhost:9090/Unit/AddUnit", this.Unit).then(res => {
        if (res !== "") {
          this.$message.success("单位添加成功")
        } else {
          this.$message.error("单位添加失败")
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <el-card shadow="always" >
      <el-form :model="Unit" label-width="100px" :rules="rules">
        <el-form-item label="单位名称" prop="unitName">
          <el-input style="font-size: 16px" v-model="Unit.unitName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input style="font-size: 16px" v-model="Unit.contactPerson" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input style="font-size: 16px" v-model="Unit.contactNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址" prop="emailAddress">
          <el-input style="font-size: 16px" v-model="Unit.emailAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系地址" prop="contactAddress">
          <el-input style="font-size: 16px" v-model="Unit.contactAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单位性质">
          <el-input style="font-size: 16px" v-model="Unit.nature" autocomplete="off" ></el-input>
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