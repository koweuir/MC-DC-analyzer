<script>

export default{
  name: "UnitList",
  created() {
    // 请求分页查询数据
    this.load()
  },
  data(){
    return{
      readOnly: true,
      dialogFormVisible: false,
      unitInfo:{},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      currentUnitName: ""
    }
  },
  methods:{
    load(){
      this.request.get("http://localhost:9090/Unit/GetUnitList",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          UnitName: this.currentUnitName
        }
      }).then( res=>{
        this.tableData = res.data
        this.total = res.total
      })
    },
    close(){
      if(!this.readOnly){
        this.request.post("http://localhost:9090/Unit/Update",this.unitInfo).then(res=>{
          if (res !== "") {
            this.$message.success("单位信息修改成功")
            this.load()
          } else {
            this.$message.error("单位信息修改失败")
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
      this.unitInfo = row;
    },
    handleEdit(row) {
      this.readOnly = false;
      this.dialogFormVisible = true;
      this.unitInfo = row;
    },
    handleDelete(row) {
      this.unitInfo = row;
      let book={
        unitName:this.unitInfo.unitName
      }
      this.request.post("http://localhost:9090/Book/SearchBookByUnit",book).then(res=>{
        if(res!==""){
          this.$message.error("该单位存在书籍，无法进行删除")
        }
        else{
          let user={
            unitName:this.unitInfo.unitName
          }
          this.request.post("http://localhost:9090/User/GetUserByUnit",user).then(res=>{
            if(res!==""){
              this.$message.error("该单位存在用户，无法进行删除")
            }else{
              this.request.post("http://localhost:9090/Unit/Delete",this.unitInfo).then(res=>{
                if (res > 0) {
                  this.$message.success("单位删除成功")
                  this.load()
                } else {
                  this.$message.error("单位删除失败")
                }
              })
            }
          })
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <div style="margin: 10px 0;" align="center">
      <el-input style="width: 200px;" placeholder="请输入单位名称" suffix-icon="el-icon-search" v-model="currentUnitName"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData" border-bottom stripe>
      <el-table-column prop="unitId" label="单位ID" />
      <el-table-column prop="unitName" label="单位名称" />
      <el-table-column prop="contactPerson" label="联系人"/>
      <el-table-column prop="contactNumber" width="220px" label="联系电话" />
      <el-table-column prop="emailAddress" width="160px" label="邮箱地址" />
      <el-table-column prop="contactAddress" width="240px" label="联系地址" />
      <el-table-column prop="nature" width="250px" label="单位性质" />
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
    <el-dialog title="单位详细信息" :visible.sync="dialogFormVisible" :before-close="close" >
      <el-form :model="unitInfo" label-width="100px">
        <el-form-item label="单位名称" >
          <el-input style="font-size: 16px" v-model="unitInfo.unitName" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="联系人">
          <el-input style="font-size: 16px" v-model="unitInfo.contactPerson" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input style="font-size: 16px" v-model="unitInfo.contactNumber" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址">
          <el-input style="font-size: 16px" v-model="unitInfo.emailAddress" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="联系地址">
          <el-input style="font-size: 16px" v-model="unitInfo.contactAddress" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="单位性质">
          <el-input style="font-size: 16px" v-model="unitInfo.nature" autocomplete="off" :readonly="readOnly"></el-input>
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