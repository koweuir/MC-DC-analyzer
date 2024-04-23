<script>
export default {
  name: "BookCirculation",
  created() {
    // 请求分页查询数据
    this.load()
  },
  data() {
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      readOnly: true,
      dialogFormVisible: false,
      circulationVisible: false,
      bookInfo:{},
      tableData: [],
      options: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      currentBookName: "",
      currentBookId:"",
      currentAuthor:"",
      currentPublisher:"",
      Reason:"",
      Comment:""
    }
  },
  methods:{
    load(){
      this.request.get("http://localhost:9090/Book/GetBookList",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          bookName: this.currentBookName,
          bookId: this.currentBookId,
          author: this.currentAuthor,
          publisher: this.currentPublisher,
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
        this.request.post("http://localhost:9090/Book/Update",this.bookInfo).then(res=>{
          if (res !== "") {
              this.$message.success("图书信息修改成功")
              this.load()
          } else {
            this.$message.error("图书信息修改失败")
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
      this.bookInfo = row;
    },
    handleEdit(row) {
      this.bookInfo = row;
      if(this.bookInfo.unitName !== this.user.unitName){
        this.$message.warning("无法操作不属于本单位的图书")
      }else {
        this.readOnly = false;
        this.dialogFormVisible = true;
      }
    },
    handleDelete(row) {
      this.bookInfo = row;
      if(this.bookInfo.unitName !== this.user.unitName){
        this.$message.warning("无法操作不属于本单位的图书")
      }else{
        if(this.bookInfo.bookStatus === "借出"){
          this.$message.warning("该图书被借出，无法进行删除")
        }else if(this.bookInfo.bookCirculation !== "归还"){
          this.$message.warning("该图书被流通，无法进行删除")
        }
        else{
          this.request.post("http://localhost:9090/Book/Delete",this.bookInfo).then(res=>{
            if (res > 0) {
              this.$message.success("图书删除成功")
              this.load()
            } else {
              this.$message.error("图书删除失败")
            }
          })
        }
      }
    },
    handleCirculation(row){
      this.bookInfo = row;
      if(this.bookInfo.unitName === this.user.unitName){
        this.$message.warning("无法对本单位的图书进行流通")
      }
      else{
        if(this.bookInfo.bookStatus === "借出"){
          this.$message.warning("该图书被借出，无法进行流通")
        }
        else{
          if(this.bookInfo.bookCirculation === "流通"){
            this.$message.warning("该图书在流通中，无法进行流通")
          }
          else{
            this.circulationVisible = true
          }
        }
      }
    },
    Circulate(){
      let circulation={
        bookId: this.bookInfo.bookId,
        unitFrom: this.bookInfo.unitName,
        unitTo: this.user.unitName,
        circulationTime: null,
        returnTime: null,
        reason: this.Reason,
        userName: this.user.userName,
        phone: this.user.phone,
        circulationStatus: null,
        comment: this.Comment
      }
      this.bookInfo.bookCirculation = "流通"
      this.request.post("http://localhost:9090/Book/Update",this.bookInfo).then(res=>{
        if (res !== "") {
          this.$message.success("图书流通信息修改成功")
          this.load()
        } else {
          this.$message.error("图书流通信息修改失败")
        }
      })
      this.request.post("http://localhost:9090/Circulation/Update", circulation).then(res=>{
        if (res !== "") {
          this.$message.success("申请流通成功，请耐心等待审核")
        } else {
          this.$message.error("申请流通失败")
        }
      })
      this.circulationVisible = false
    },
    clear(){
      this.Reason="";
      this.Comment=""
      this.circulationVisible = false
    }
  }
}
</script>

<template>
  <div>
    <div style="margin: 10px 0;" align="center">
      <el-input style="width: 200px; margin-right: 5px" placeholder="图书名称" suffix-icon="el-icon-reading" v-model="currentBookName"></el-input>
      <el-input style="width: 200px; margin-right: 5px" placeholder="图书编号" suffix-icon="el-icon-collection" v-model="currentBookId"></el-input>
      <el-input style="width: 200px; margin-right: 5px" placeholder="作者" suffix-icon="el-icon-user-solid" v-model="currentAuthor"></el-input>
      <el-input style="width: 200px; margin-right: 5px" placeholder="出版社" suffix-icon="el-icon-office-building" v-model="currentPublisher"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData" border-bottom stripe>
      <el-table-column prop="bookId" label="图书编号" />
      <el-table-column prop="bookName" label="图书名" />
      <el-table-column prop="publicationTime" width="220px" label="出版时间"/>
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" width="160px" label="出版社" />
      <el-table-column prop="bookStatus" width="160px" label="图书借阅状态" />
      <el-table-column prop="unitName" width="200px" label="图书所属单位" />
      <el-table-column prop="operation" label="操作" width="430px">
        <template slot-scope="scope">
          <el-button size="medium" type="warning" @click="handleInfo(scope.row)">详情 <i class="el-icon-info"></i></el-button>
          <el-button size="medium" type="success" @click="handleEdit(scope.row)">修改 <i class="el-icon-edit"></i></el-button>
          <el-button size="medium" type="danger" @click="handleDelete(scope.row)">删除 <i class="el-icon-remove-outline"></i></el-button>
          <el-button size="medium" type="primary" @click="handleCirculation(scope.row)">流通 <i class="el-icon-s-promotion"></i></el-button>
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
    <el-dialog title="图书详细信息" :visible.sync="dialogFormVisible" :before-close="close" >
      <el-form :model="bookInfo" label-width="100px">
        <el-form-item label="图书名" >
          <el-input style="font-size: 16px" v-model="bookInfo.bookName" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="出版时间">
          <el-date-picker
              v-model="bookInfo.publicationTime"
              type="date"
              placeholder="选择日期"
              :readonly="readOnly">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="作者">
          <el-input style="font-size: 16px" v-model="bookInfo.author" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="出版社">
          <el-input style="font-size: 16px" v-model="bookInfo.publisher" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="图书分类">
          <el-input style="font-size: 16px" v-model="bookInfo.category" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input style="font-size: 16px" v-model="bookInfo.price" autocomplete="off" :readonly="readOnly"></el-input>
        </el-form-item>
        <el-form-item label="图书借阅状态">
          <el-input style="font-size: 16px" v-model="bookInfo.bookStatus" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="单位名称">
          <el-select style="font-size: 16px" v-model="bookInfo.unitName" placeholder="请选择图书所属单位" autocomplete="off" :disabled="readOnly">
            <el-option
                v-for="(item,index) in options"
                :key="index"
                :label="item.unitName"
                :value="item.unitName">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="close">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="流通信息" :visible.sync="circulationVisible" :before-close="clear">
      <el-form label-width="100px">
        <el-form-item label="流通理由">
          <el-input style="font-size: 16px" v-model="Reason" clearable autocomplete="off" placeholder="请输入流通理由"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input style="font-size: 16px" v-model="Comment" clearable autocomplete="off" placeholder="请输入备注"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clear">取 消 流 通</el-button>
        <el-button type="primary" @click="Circulate">确 定 流 通</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style></style>