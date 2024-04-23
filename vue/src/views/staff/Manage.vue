<template>
  <div>
    <div v-if="isUserLoggedIn">
      <el-container style="min-height: 100vh;">
        <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
          <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" style="padding-bottom: 20px"/>
        </el-aside>
        <el-container>
          <el-header style="border-bottom: 1px solid #ccc;">
            <Header :co1lapseBtnClass="co1lapseBtnClass" :collapse="collapse"/>
          </el-header>
          <el-main>
            <router-view />
          </el-main>
        </el-container>
      </el-container>
    </div>
    <div v-else key="false">
      <el-dialog
          title="提示"
          width="30%"
          :visible.sync="dialogVisible"
          :before-close="handleClose">
        <span style="font-size: 16px"><b>用户未登录，请先登录！</b></span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import Aside from "@/components/staff/Aside.vue";
import Header from "@/components/staff/Header.vue";
export default {
  name: 'Manage',
  data() {
    return {
      isUserLoggedIn: false, // 标识用户是否已登录
      dialogVisible: false ,// 控制提示框的显示与隐藏
      co1lapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true
    }
  },
  components: {
    Aside,Header
  },
  created() {
    this.checkUserLoggedIn();
  },
  methods:{
    handleClose(){
      this.$router.push("/")
    },
    checkUserLoggedIn() {
      let user = localStorage.getItem("user");
      if (user) {
        this.isUserLoggedIn = true;
      } else {
        this.dialogVisible = true; // 显示提示框
      }
    },
    collapse(){//点击收缩按钮触发
      this.isCollapse=!this.isCollapse;
      if(this.isCollapse){//收缩
        this.sideWidth = 64
        this.co1lapseBtnClass='el-icon-s-unfold'
        this.logoTextShow = false
      }else{//展开
        this.sideWidth = 200
        this.co1lapseBtnClass='el-icon-s-fold'
        this.logoTextShow = true
      }
    }
  }
}
</script>
