<script>
export default {
  name: "BookList",
  created() {
    // 请求分页查询数据
    this.load()
  },
  data() {
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      readOnly: true,
      dialogFormVisible: false,
      tableOrView: true,
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
    }
  },
  methods:{
    load(){
      this.request.get("http://localhost:9090/Book/GetPermissionBookList",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          bookName: this.currentBookName,
          bookId: this.currentBookId,
          author: this.currentAuthor,
          publisher: this.currentPublisher,
          unitName: this.user.unitName,
          permission: "开放"
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
    }
  }
}
</script>

<template>
  <div>
    <div>
      <el-switch
          v-model="tableOrView"
          active-color="#31CE23"
          inactive-color="#2D95FF"
          active-icon-class="el-icon-menu"
          inactive-icon-class="el-icon-picture">
      </el-switch>
    </div>
    <div style="margin: 10px 0;" align="center">
      <el-input style="width: 200px; margin-right: 5px" placeholder="图书名称" suffix-icon="el-icon-reading" v-model="currentBookName"></el-input>
      <el-input style="width: 200px; margin-right: 5px" placeholder="图书编号" suffix-icon="el-icon-collection" v-model="currentBookId"></el-input>
      <el-input style="width: 200px; margin-right: 5px" placeholder="作者" suffix-icon="el-icon-user-solid" v-model="currentAuthor"></el-input>
      <el-input style="width: 200px; margin-right: 5px" placeholder="出版社" suffix-icon="el-icon-office-building" v-model="currentPublisher"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData" border-bottom stripe v-if="tableOrView">
      <el-table-column prop="bookId" label="图书编号" />
      <el-table-column prop="bookName" label="图书名" />
      <el-table-column prop="publicationTime" width="150px" label="出版时间"/>
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" width="100px" label="出版社" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="category" width="160px" label="图书分类" />
      <el-table-column prop="bookStatus" width="200px" label="图书借阅状态" />
      <el-table-column prop="unitName" width="140px" label="图书所属单位" />
      <el-table-column prop="operation" label="操作" width="330px">
        <template slot-scope="scope">
          <el-button size="medium" type="warning" @click="handleInfo(scope.row)">详情 <i class="el-icon-info"></i></el-button>
          <el-button size="medium" type="success" @click="handleEdit(scope.row)">修改 <i class="el-icon-edit"></i></el-button>
          <el-button size="medium" type="danger" @click="handleDelete(scope.row)">删除 <i class="el-icon-remove-outline"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-table :data="tableData" border-bottom stripe v-else key="false"  row-class-name="table-row">
      <el-table-column prop="bookId" label="图书编号" />
      <el-table-column prop="bookName" label="图书名" />
      <el-table-column prop="publicationTime" width="100px" label="出版时间"/>
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" label="出版社" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="bookPicturePath" width="300px" label="图片">
        <template slot-scope="scope">
          <el-image v-if="scope.row.bookPicturePath" :src="scope.row.bookPicturePath" fit="cover" :style="{ width: '180px', height: '200px' }"></el-image>
          <i v-else class="el-icon-picture"></i>
        </template>
      </el-table-column>
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
          <el-select style="font-size: 16px" v-model="bookInfo.unitName" placeholder="请选择图书所属单位" :disabled="readOnly" autocomplete="off">
            <el-option
                v-for="(item,index) in options"
                :key="index"
                :label="item.unitName"
                :value="item.unitName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书权限">
          <el-select style="font-size: 16px" v-model="bookInfo.permission" placeholder="请选择图书权限" autocomplete="off" :disabled="readOnly">
            <el-option label="开放" value="开放"></el-option>
            <el-option label="隐藏" value="隐藏"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="close">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
.table-row {
  height: 280px; /* 设置表格行的高度为 80px */
}
</style>