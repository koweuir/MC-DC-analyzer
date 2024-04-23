<script>

export default {
  name: "Borrow",
  created() {
    // 请求分页查询数据
    this.load('待审核')
  },
  data() {
    return {
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      activeTab: '待审核',
      borrowInfo:{}
    };
  },
  methods: {
    handleTabClick(tab){
      if (tab.name === '待审核') {
        this.load('待审核');
      } else if (tab.name === '已借出') {
        this.load('已借出');
      } else if (tab.name === '已结束') {
        this.load('已结束');
      }
    },
    load(status){
      this.request.get("http://localhost:9090/Borrow/GetOutList",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          unitName: this.user.unitName,
          borrowStatus: status
        }
      }).then( res=>{
        this.tableData = res.data
        this.total = res.total
      })
    },
    handleSizeChange(pageSize,status){
      this.pageSize = pageSize
      this.load(status)
    },
    handleCurrentChange(pageNum,status){
      this.pageNum = pageNum
      this.load(status)
    },
    firstAgree(row){
      this.borrowInfo = row
      this.borrowInfo.borrowTime = new Date().toLocaleString()
      this.borrowInfo.borrowStatus = "已审核"
      this.request.post("http://localhost:9090/Borrow/Update",this.borrowInfo).then(res=>{
        if (res !== "") {
          let book={
            bookId: this.borrowInfo.bookId,
            bookStatus: "借出"
          }
          this.request.post("http://localhost:9090/Book/Update",book).then(res=>{
            if(res!==""){
              this.$message.success("完成")
              this.load()
            }
          })
        } else {
          this.$message.error("失败")
        }
      })
    },
    firstRefuse(row){
      this.borrowInfo = row
      this.borrowInfo.borrowStatus = "已结束"
      let book={
        bookId: this.borrowInfo.bookId,
        bookStatus: "在库"
      }
      this.request.post("http://localhost:9090/Book/Update",book).then(res=>{
        if(res!==""){
          this.request.post("http://localhost:9090/Borrow/Update",this.borrowInfo).then(res=>{
            if (res !== "") {
              this.$message.success("完成")
              this.load()
            } else {
              this.$message.error("失败")
            }
          })
        }
      })
    },
    secondAgree(row){
      this.borrowInfo = row
      this.borrowInfo.returnTime = new Date().toLocaleString()
      this.borrowInfo.borrowStatus = "已结束"
      this.request.post("http://localhost:9090/Borrow/Update",this.borrowInfo).then(res=>{
        if (res !== "") {
          let book={
            bookId: this.borrowInfo.bookId,
            bookStatus:"在库"
          }
          this.request.post("http://localhost:9090/Book/Update",book).then(res=>{
            if(res!==""){
              this.$message.success("完成")
              this.load()
            }
          })
        }
        else {
          this.$message.error("失败")
        }
      })
    },
    secondRefuse(row){
      this.borrowInfo = row
      this.borrowInfo.borrowStatus = "已审核"
      this.request.post("http://localhost:9090/Borrow/Update",this.borrowInfo).then(res=>{
        if (res !== "") {
          this.$message.success("完成")
          this.load()
        } else {
          this.$message.error("失败")
        }
      })
    },
    formatCirculationStatus(row) {
      if (row.borrowStatus === '已借出') {
        return '归还中';
      } else {
        return "";
      }
    }
  }
}
</script>

<template>
  <el-tabs type="card" v-model="activeTab" @tab-click="handleTabClick">
    <el-tab-pane name="待审核">
      <span slot="label"><i class="el-icon-loading"></i> 待审核</span>
      <el-table :data="tableData" border-bottom stripe>
        <el-table-column prop="borrowId" label="借阅ID" />
        <el-table-column prop="bookId" label="图书ID" />
        <el-table-column prop="unitName" label="借出单位"/>
        <el-table-column prop="userName" label="借入人" />
        <el-table-column prop="borrowTime" width="160px" label="借阅时间" />
        <el-table-column prop="returnTime" width="160px" label="归还时间" />
        <el-table-column prop="reason" width="120px" label="借阅理由" />
        <el-table-column prop="phone"  width="120px" label="借阅人联系方式" />
        <el-table-column prop="borrowStatus"  label="借阅状态" />
        <el-table-column prop="comment"  label="备注" />
        <el-table-column prop="operation" label="操作" width="220px">
          <template slot-scope="scope">
            <el-button size="medium" type="success" @click="firstAgree(scope.row)">同意 <i class="el-icon-check"></i></el-button>
            <el-button size="medium" type="danger" @click="firstRefuse(scope.row)">拒绝 <i class="el-icon-close"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0;">
        <el-pagination
            @size-change="(val) => handleSizeChange(val, '待审核')"
            @current-change="(val) => handleCurrentChange(val, '待审核')"
            :current-page="pageNum"
            :page-sizes="[2, 5, 10]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </el-tab-pane>
    <el-tab-pane name="已借出">
      <span slot="label"><i class="el-icon-circle-check"></i> 已借出</span>
      <el-table :data="tableData" border-bottom stripe>
        <el-table-column prop="borrowId" label="借阅ID" />
        <el-table-column prop="bookId" label="图书ID" />
        <el-table-column prop="unitName" label="借出单位"/>
        <el-table-column prop="userName" label="借入人" />
        <el-table-column prop="borrowTime" width="160px" label="借阅时间" />
        <el-table-column prop="returnTime" width="160px" label="归还时间" />
        <el-table-column prop="reason" width="120px" label="借阅理由" />
        <el-table-column prop="phone"  width="120px" label="借阅人联系方式" />
        <el-table-column prop="borrowStatus"  label="借阅状态" />
        <el-table-column prop="comment"  label="备注" />
        <el-table-column prop="operation" label="操作" width="220px">
          <template slot-scope="scope">
            <el-button size="medium" type="success" @click="secondAgree(scope.row)">同意 <i class="el-icon-check"></i></el-button>
            <el-button size="medium" type="danger" @click="secondRefuse(scope.row)">拒绝 <i class="el-icon-close"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0;">
        <el-pagination
            @size-change="(val) => handleSizeChange(val, '已借出')"
            @current-change="(val) => handleCurrentChange(val, '已借出')"
            :current-page="pageNum"
            :page-sizes="[2, 5, 10]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </el-tab-pane>
    <el-tab-pane name="已结束">
      <span slot="label"><i class="el-icon-s-check"></i> 已结束</span>
      <el-table :data="tableData" border-bottom stripe>
        <el-table-column prop="borrowId" label="借阅ID" />
        <el-table-column prop="bookId" label="图书ID" />
        <el-table-column prop="unitName" label="借出单位"/>
        <el-table-column prop="userName" label="借入人" />
        <el-table-column prop="borrowTime" width="160px" label="借阅时间" />
        <el-table-column prop="returnTime" width="160px" label="归还时间" />
        <el-table-column prop="reason" width="120px" label="借阅理由" />
        <el-table-column prop="phone"  width="120px" label="借阅人联系方式" />
        <el-table-column prop="borrowStatus"  label="借阅状态" />
        <el-table-column prop="comment"  label="备注" />
        <el-table-column prop="operation" label="操作" width="220px">
          <template slot-scope="scope">
            <el-button size="medium" type="success" disabled>同意 <i class="el-icon-check"></i></el-button>
            <el-button size="medium" type="danger" disabled>拒绝 <i class="el-icon-close"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0;">
        <el-pagination
            @size-change="(val) => handleSizeChange(val, '已结束')"
            @current-change="(val) => handleCurrentChange(val, '已结束')"
            :current-page="pageNum"
            :page-sizes="[2, 5, 10]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>

<style>

</style>