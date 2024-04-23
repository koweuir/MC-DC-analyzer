<script>

export default {
  name: "In",
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
      circulationInfo:{},
      currentBook: {}
    };
  },
  methods: {
    handleTabClick(tab){
      if (tab.name === '待审核') {
        this.load('待审核');
      } else if (tab.name === '已审核') {
        this.load('已审核');
      } else if (tab.name === '已结束') {
        this.load('已结束');
      }
    },
    load(status){
      this.request.get("http://localhost:9090/Circulation/GetInList",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          unitTo: this.user.unitName,
          circulationStatus: status
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
    returnBack(row){
      this.circulationInfo = row
      let book = {
        bookId: this.circulationInfo.bookId
      }
      this.request.post("http://localhost:9090/Book/FindBook",book).then(res=>{
        if (res !== "") {
          if(res.bookStatus !== "在库"){
            this.$message.error("图书被借出，无法进行归还")
          }
          else{
            this.circulationInfo.circulationStatus = "已流出"
            this.request.post("http://localhost:9090/Circulation/Update",this.circulationInfo).then(res=>{
              if (res !== "") {
                this.$message.success("完成")
                this.load()
              } else {
                this.$message.error("失败")
              }
            })
          }
        } else {
          this.$message.error("出错！！！")
        }
      })
    }
  }
}
</script>

<template>
  <el-tabs type="card" v-model="activeTab" @tab-click="handleTabClick">
    <el-tab-pane name="待审核">
      <span slot="label"><i class="el-icon-loading"></i> 待审核</span>
      <el-table :data="tableData" border-bottom stripe>
        <el-table-column prop="circulationId" label="流通ID" />
        <el-table-column prop="bookId" label="图书ID" />
        <el-table-column prop="unitFrom" label="所属单位"/>
        <el-table-column prop="unitTo" label="流入单位" />
        <el-table-column prop="circulationTime" width="160px" label="流通时间" />
        <el-table-column prop="returnTime" width="160px" label="归还时间" />
        <el-table-column prop="reason" width="120px" label="流通理由" />
        <el-table-column prop="userName"  label="申请人" />
        <el-table-column prop="phone"  width="120px" label="申请人联系方式" />
        <el-table-column prop="circulationStatus"  label="流通状态" />
        <el-table-column prop="comment"  label="备注" />
        <el-table-column prop="operation" label="操作" width="120px">
          <template slot-scope="scope">
            <el-button size="medium" type="warning" disabled>归还 <i class="el-icon-refresh-left"></i></el-button>
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
    <el-tab-pane name="已审核">
      <span slot="label"><i class="el-icon-circle-check"></i> 已审核</span>
      <el-table :data="tableData" border-bottom stripe>
        <el-table-column prop="circulationId" label="流通ID" />
        <el-table-column prop="bookId" label="图书ID" />
        <el-table-column prop="unitFrom" label="所属单位"/>
        <el-table-column prop="unitTo" label="流入单位" />
        <el-table-column prop="circulationTime" width="160px" label="流通时间" />
        <el-table-column prop="returnTime" width="160px" label="归还时间" />
        <el-table-column prop="reason" width="120px" label="流通理由" />
        <el-table-column prop="userName"  label="申请人" />
        <el-table-column prop="phone"  width="120px" label="申请人联系方式" />
        <el-table-column prop="circulationStatus"  label="流通状态" />
        <el-table-column prop="comment"  label="备注" />
        <el-table-column prop="operation" label="操作" width="120px">
          <template slot-scope="scope">
            <el-button size="medium" type="warning" @click="returnBack(scope.row)">归还 <i class="el-icon-refresh-left"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0;">
        <el-pagination
            @size-change="(val) => handleSizeChange(val, '已审核')"
            @current-change="(val) => handleCurrentChange(val, '已审核')"
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
        <el-table-column prop="circulationId" label="流通ID" />
        <el-table-column prop="bookId" label="图书ID" />
        <el-table-column prop="unitFrom" label="所属单位"/>
        <el-table-column prop="unitTo" label="流入单位" />
        <el-table-column prop="circulationTime" width="160px" label="流通时间" />
        <el-table-column prop="returnTime" width="160px" label="归还时间" />
        <el-table-column prop="reason" width="120px" label="流通理由" />
        <el-table-column prop="userName"  label="申请人" />
        <el-table-column prop="phone"  width="120px" label="申请人联系方式" />
        <el-table-column prop="circulationStatus"  label="流通状态" />
        <el-table-column prop="comment"  label="备注" />
        <el-table-column prop="operation" label="操作" width="120px">
          <template slot-scope="scope">
            <el-button size="medium" type="warning" disabled>归还 <i class="el-icon-refresh-left"></i></el-button>
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

<style></style>