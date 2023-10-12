<template>
  <div>
<!--      新增按钮-->
      <div style="margin: 10px 0">
        <el-button type="primary" @click="add">新增</el-button>
        <el-button>其它</el-button>
      </div>
<!--    搜索框-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder=" 请 输 入 关 键 字 " style="width:30%"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="list">查询</el-button>
    </div>

      <el-table :data="tableData" stripe style="width: 90%"><!--data="tableData"在下面的data中获取数据-->
        <el-table-column
            prop="id"
            label="ID" sortable
        >
        </el-table-column>
        <el-table-column
            prop="name"
            label="家居名" >
        </el-table-column>
        <el-table-column
            prop="maker"
            label="厂家">
        </el-table-column>
        <el-table-column
            prop="price"
            label="价格">
        </el-table-column>
        <el-table-column
            prop="sales"
            label="销量">
        </el-table-column>
        <el-table-column
            prop="stock"
            label="库存">
        </el-table-column>
<!--      增加相关操作-->
      <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit1(scope.row)" type="primary">编辑</el-button>
        <!--  引入一个确认框  -->
<!--          <el-button type="text" @click="deleteById(scope.row)">删除</el-button>-->
          <el-popconfirm
              title="确定删除吗？" @confirm="deleteById(scope.row)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>


    </el-table>
    <!--    弹出框-->
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px" :rules="rules" ref="form">
        <el-form-item label="家居名" prop="name">
          <el-input v-model="form.name" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="厂商" prop="maker">
          <el-input v-model="form.maker" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handlePageSizeChange" @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5,10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script>

import request from "@/utils/request";
export default {
  name: 'HomeView',
  components: {
    // HelloWorld
  },
  data() {
    return {
      search:"",
      currentPage:1,
      pageSize:5,
      total:10,
      form:{},
      dialogVisible: false,//控制弹出框的显示与隐藏
      tableData: [],//表格数据
      //表单验证
      rules: {
        name: [
          {required: true, message: '请输入称家居名', trigger: 'blur'}
        ],maker: [
          {required: true, message: '请输入称制造商', trigger: 'blur'}
        ],price: [
          {required: true, message: '请输入价格', trigger: 'blur'}, {pattern: /^(([1-9]\d*)|(0))(\.\d+)?$/, message: '请输入数字', trigger: 'blur'}
        ],sales: [
          {required: true, message: '请输入销量', trigger: 'blur'}, {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入数字', trigger: 'blur'}
        ],stock: [
          {required: true, message: '请输入库存', trigger: 'blur'}, {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入数字', trigger: 'blur'}
        ]
        }
      }
  },
  created() {
    this.list()
  },
  methods:{
    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize
      this.list()
    },
    handleCurrentChange(pageNum) {
//处理每页显示多少条记录变化
      this.currentPage = pageNum
      this.list()
    },
    deleteById(row){
      request.delete("/api/deleteById/id="+row.id).then(res => {
        console.log(res)
        if(res.code==="200"){
          this.$message({
            message: "删除成功",
            type: "success"
          })
        }else {
          this.$message({
            message: "删除失败",
            type: res.msg
          })
        }
        this.list()
      })
    },
    list(){
      request.get("/api/furnsByPageAndCondition",{
            params: {
              pageNum: this.currentPage,
              pageSize: this.pageSize,
              search: this.search
            }
          }
      ).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleEdit(row){//在页面拿到值
      console.log("row", row)
      console.log("row1",JSON.stringify(row))
      console.log("row2",JSON.parse(JSON.stringify(row)))//得到的是一个对象
      this.form = JSON.parse(JSON.stringify(row))//将row的值赋给form
      this.dialogVisible= true
    },
    handleEdit1(row){//在数据库拿到值
      request.get("/api/getById/id="+row.id).then(res => {
        console.log(res)
        this.form = res.data
        this.dialogVisible = true
      })},
    add(){
      this.dialogVisible = true
      this.form = {}//在每次点击之后，清空表单
      //将上次表单验证的信息清空
    },
    save(){//保存和更新都会走这个方法。而两者的区别是，保存不传id，更新传id
      if(this.form.id){
        request.put("/api/update", this.form).then(
            res => {
              console.log(res)
              if (res.code === "200") {
                this.$message({
                  message: "更新成功",
                  type: "success"
                })
              }else {
                this.$message({
                  message: "更新失败",
                  type: "error"
                })
              }
          this.list()
          this.dialogVisible = false
        })
      } else {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            request.post("/api/save", this.form).then(res => {
              this.dialogVisible = false
              this.list()
              if (res.code === "200") {
                this.$message({
                  message: "添加成功",
                  type: "success"
                })
              } else {
                this.$message({
                  message: "添加失败",
                  type: "error"
                })
              }
            })
          } else {
            this.$message({//弹出更新失败信息
              type: "error", message: "验证失败，不提交"
            })
            return false
        }})
      }
    }
  }
}
</script>
