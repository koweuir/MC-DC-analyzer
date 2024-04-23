<script>

export default{
  name: "User",
  created() {
    // 请求分页查询数据
    this.load()
  },
  data(){
    return{
      readOnly: true,
      dialogFormVisible: false,
      userinfo:{},
      tableData: [],
      options: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      currentUser:""
    }
  },
  methods:{
    load(){
      this.request.get("http://localhost:9090/User/GetUserList",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userName: this.currentUser
        }
      }).then( res=>{
        this.tableData = res.data
        this.total = res.total
      })
      //获取单位信息
      this.request.post("http://localhost:9090/Unit/GetAllUnits").then(res => {
        this.options = res;
      })
    },
    close(){
      if(!this.readOnly){
        this.request.post("http://localhost:9090/User/Update",this.userinfo).then(res=>{
          if (res !== "") {
            if(res.userName === "#@重名#@"){
              this.$message.warning("当前用户的用户名已存在")
            }
            else {
              this.$message.success("用户信息修改成功")

              this.load()
            }
          } else {
            this.$message.error("用户信息修改失败")
          }
        })
      }
      this.readOnly = true;
      this.dialogFormVisible = false
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    handleInfo(row) {
      this.dialogFormVisible = true;
      this.userinfo = row;
    },
    handleEdit(row) {
      this.readOnly = false;
      this.dialogFormVisible = true;
      this.userinfo = row;
    },
    handleDelete(row) {
      this.userinfo = row;
      this.request.post("http://localhost:9090/User/Delete",this.userinfo).then(res=>{
        if (res > 0) {
          this.$message.success("用户删除成功")
          this.load()
        } else {
          this.$message.error("用户删除失败")
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <div style="margin: 10px 0;" align="center">
      <el-input style="width: 200px;" placeholder="请输入用户名称" suffix-icon="el-icon-search" v-model="currentUser"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData" border-bottom stripe>
      <el-table-column prop="userId" label="用户ID" />
      <el-table-column prop="userName" label="用户名" />
      <el-table-column prop="characterName" label="用户身份"/>
      <el-table-column prop="email" width="220px" label="邮箱" />
      <el-table-column prop="phone" width="160px" label="联系电话" />
      <el-table-column prop="unitName" width="240px" label="所属单位" />
      <el-table-column prop="lastLoginTime" width="250px" label="最后登录时间" />
      <el-table-column prop="operation" label="操作" width="330px">
        <template slot-scope="scope">
          <el-button size="medium" type="warning" @click="handleInfo(scope.row)">详情 <i class="el-icon-info"></i></el-button>
          <el-button size="medium" type="success" @click="handleEdit(scope.row)">修改 <i class="el-icon-edit"></i></el-button>
          <el-button size="medium" type="danger" @click="handleDelete(scope.row)">删除 <i class="el-icon-remove-outline"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="用户详细信息" :visible.sync="dialogFormVisible" :before-close="close" >
      <el-form :model="userinfo" label-width="100px">
        <el-form-item label="用户名" >
          <el-input style="font-size: 16px" v-model="userinfo.userName" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="用户角色">
          <el-select style="font-size: 16px" v-model="userinfo.characterName" placeholder="请选择用户角色" :disabled="readOnly" autocomplete="off">
            <el-option label="管理员" value="管理员"></el-option>
            <el-option label="工作人员" value="工作人员"></el-option>
            <el-option label="读者" value="读者"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input style="font-size: 16px" v-model="userinfo.trueName" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select style="font-size: 16px" v-model="userinfo.sex" placeholder="请选择用户性别" autocomplete="off" :disabled="readOnly">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
            <el-option label="保密" value="保密"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话">
          <el-input style="font-size: 16px" v-model="userinfo.phone" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input style="font-size: 16px" v-model="userinfo.email" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input style="font-size: 16px" v-model="userinfo.address" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="单位名称">
          <el-select style="font-size: 16px" v-model="userinfo.unitName" placeholder="请选择用户所属单位" autocomplete="off" :disabled="readOnly">
            <el-option
                v-for="(item,index) in options"
                :key="index"
                :label="item.unitName"
                :value="item.unitName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最后登录时间">
          <el-input style="font-size: 16px" v-model="userinfo.lastLoginTime" autocomplete="off" readonly></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="close">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style scoped>
</style>