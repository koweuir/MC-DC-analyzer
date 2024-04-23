<script>

export default {
  name: "Analysis",
  data(){
    return{
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      currentBooks:"",
      currentBooksInUnit:"",
      circulationInBooks:"",
      circulationOutBooks:""
    }
  },
  created(){
    this.count()
  },
  mounted() {
    // 在生命周期中调用 getRenderer 方法
    this.getRenderer();
  },
  methods:{
    getRenderer() {
      // 基于准备好的 dom，初始化 echarts 实例
      let EChart = this.$echarts.init(document.getElementById("EChart"));

      // 配置参数
      let config = {
        title: {
          text: "按月统计的图书借阅信息折线图",
          left: "center",
        },
        tooltip: {},
        xAxis: {
          data: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
        },
        yAxis: {
          name: "图书（本）", // 设置 Y 轴名称
        },
        series: [
          {
            name: "借阅数量",
            type: "line",
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
          },
        ],
      };

      // 调用后端接口获取借阅信息的统计数据
      this.request.get("http://localhost:9090/Borrow/GetCountByMonth", {
        params: {
          unitName: this.user.unitName,
        },
      }).then((response) => {
        // 获取到后端返回的数据
        let data = response;
        // 根据后端数据进行月份赋值
        if (data && data.length) {
          for (let i = 0; i < data.length; i++) {
            let item = data[i];
            let month = item.month;
            let count = item.count;
            let index = parseInt(month.split("-")[1]) - 1;
            config.series[0].data[index] = count;
          }
        }
        // 使用更新后的配置项来绘制图表
        EChart.setOption(config);
      }).catch((error) => {
        console.error("获取借阅信息统计数据失败", error);
      });
    },
    count(){
      this.request.get("http://localhost:9090/Book/CountBook",{
        params:{
          unitName:this.user.unitName,
        }
      }).then(res=>{
        this.currentBooks = res
      })
      this.request.get("http://localhost:9090/Book/CountBook",{
        params:{
          unitName:this.user.unitName,
          bookStatus: "在库"
        }
      }).then(res=>{
        this.currentBooksInUnit = res
      })
      this.request.get("http://localhost:9090/Circulation/CountCirculation",{
        params:{
          unitTo:this.user.unitName
        }
      }).then(res=>{
        this.circulationInBooks = res
      })
      this.request.get("http://localhost:9090/Circulation/CountCirculation",{
        params: {
          unitFrom:this.user.unitName
        }
      }).then(res=>{
        this.circulationOutBooks = res
      })
    }
  }
}
</script>

<template>
  <div>
    <div>
      <el-row :gutter="20">
        <el-col :span="6">
          <div>
            <el-statistic title="本单位总图书数" style="font-weight: bold;">
              <template slot="formatter">
                {{ currentBooks }}
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic title="当前在库总图书数" style="font-weight: bold;">
              <template slot="formatter">
                {{ currentBooksInUnit }}
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic title="本单位流入总图书数" style="font-weight: bold;">
              <template slot="formatter">
                {{ circulationInBooks }}
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-statistic title="本单位流出总图书数" style="font-weight: bold;">
              <template slot="formatter">
                {{ circulationOutBooks }}
              </template>
            </el-statistic>
          </div>
        </el-col>
      </el-row>
    </div>
    <div id="container">
      <div id="EChart" style="width: 1000px; height: 700px;"></div>
    </div>
  </div>
</template>

<style>
#container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 设置容器高度为视口高度，以垂直居中 */
}
</style>