<script>

export default {
  name: "AddBook",
  created(){
    this.request.post("http://localhost:9090/Unit/GetAllUnits").then(res => {
      this.options = res;
    })
  },
  data() {
    return{
      book:{
        bookId: null,
        bookName: null,
        publicationTime: null,
        author: null,
        publisher: null,
        category: null,
        pageNumber: null,
        price: null,
        bookStatus: null,
        unitName: null,
        bookPicturePath: null
      },
      options:[],
      rules:{
        bookId: [
          { required: true, message: '请输入图书编号', trigger: 'blur' },
        ],
        bookName: [
          { required: true, message: '请输入图书名', trigger: 'blur' },
        ],
        publicationTime: [
          { required: true, message: '请选择出版时间', trigger: 'blur' },
        ],
        author: [
          { required: true, message: '请输入作者', trigger: 'blur' },
        ],
        publisher: [
          { required: true, message: '请输入出版社', trigger: 'blur' },
        ],
        category: [
          { required: true, message: '请输入图书分类', trigger: 'blur' },
        ],
        pageNumber: [
          { required: true, message: '请输入页数', trigger: 'blur' },
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
        ],
        unitName: [
          { required: true, message: '请选择图书所在单位', trigger: 'blur' },
        ],
        permission: [
          { required: true, message: '请选择图书权限', trigger: 'blur' },
        ],
      }
    }
  },
  methods: {
    add(){
      // 获取从 el-date-picker 得到的日期
      const selectedDate = this.book.publicationTime;
      // 将日期转化为“YYYY-MM-DD”格式的字符串
      this.book.publicationTime = selectedDate.getFullYear()
          + '-' + (selectedDate.getMonth() + 1)
              .toString()
              .padStart(2, '0')
          + '-' + selectedDate.getDate()
              .toString()
              .padStart(2, '0');
      this.request.post("http://localhost:9090/Book/AddBook", this.book).then(res => {
        if (res === 1) {
          this.$message.success("图书添加成功")
        } else {
          this.$message.error("图书添加失败")
        }
      })
    },
    beforeAvatarUpload(file){
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt3M = file.size / 1024 / 1024 < 5;
      if (!isJPG && !isPNG) {
        this.$message.error("上传图片只能是 JPG 格式或 png 格式!");
      }
      if (!isLt3M) {
        this.$message.error("上传图片大小不能超过5MB!");
      }
      return (isJPG||isPNG) && isLt3M;
    },
    handleAvatarSuccess(res){
      this.book.bookPicturePath = res
    },
    success(){
      this.$message.success("批量导入成功！！！")
    }
  }
}
</script>

<template>
  <div>
    <el-card shadow="always" class="centered-form">
      <div style="text-align: right; margin-top: 10px; ">
        <el-upload   action="http://localhost:9090/Book/Import" :show-file-list="false" accept="xlsx" :on-success="success">
          <el-button type="primary">批 量 导 入</el-button>
        </el-upload>
      </div>
      <el-form :model="book" label-width="100px" :rules="rules">
        <el-form-item class="formItem" label="图书图片" >
        <el-upload
            style="margin-right: 110px"
            class="avatar-uploader"
            :action="'http://localhost:9090/Book/Upload'"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :on-success="handleAvatarSuccess">
          <el-image fit="cover" v-if="book.bookPicturePath" :src="book.bookPicturePath" class="avatar"/>
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        </el-form-item>
        <el-form-item class="formItem" label="图书编号" prop="bookId">
          <el-input style="font-size: 16px" v-model="book.bookId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="formItem" label="图书名" prop="bookName">
          <el-input style="font-size: 16px" v-model="book.bookName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="formItem" label="出版时间" prop="publicationTime">
          <el-date-picker
              v-model="book.publicationTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item class="formItem" label="作者" prop="author">
          <el-input style="font-size: 16px" v-model="book.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="formItem" label="出版社" prop="publisher">
          <el-input style="font-size: 16px" v-model="book.publisher" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item class="formItem" label="图书分类" prop="category">
          <el-input style="font-size: 16px" v-model="book.category" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item class="formItem" label="页数" prop="pageNumber">
          <el-input style="font-size: 16px" v-model="book.pageNumber" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item class="formItem" label="价格" prop="price">
          <el-input style="font-size: 16px" v-model="book.price" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-select style="font-size: 16px" v-model="book.unitName" placeholder="请选择图书所属单位" autocomplete="off">
            <el-option
                v-for="(item,index) in options"
                :key="index"
                :label="item.unitName"
                :value="item.unitName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书权限" prop="permission">
          <el-select style="font-size: 16px" v-model="book.permission" placeholder="请选择图书权限" autocomplete="off">
            <el-option label="开放" value="开放"></el-option>
            <el-option label="隐藏" value="隐藏"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer" style="text-align: center; margin-top: 10px">
      <el-button type="primary" @click="add">添 加</el-button>
    </div>
  </div>
</template>

<style>
.centered-form {
  display: flex;
  justify-content: center;
}
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
.formItem{
  width: 1000px;
}
</style>