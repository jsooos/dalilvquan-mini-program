<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="waterName">
        <el-input
          v-model="queryParams.waterName"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['water:water:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['water:water:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['water:water:remove']"
        >删除</el-button>
      </el-col>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="waterList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" align="center" prop="waterName" />
      <el-table-column label="类型" align="center" prop="waterType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.water_bucket_type" :value="scope.row.waterType"/>
        </template>
      </el-table-column>
      <el-table-column label="容量" align="center" prop="waterCapacity" />
      <el-table-column label="售价（元）" align="center" prop="waterPrice" />
      <el-table-column label="水票售价（元）" align="center" prop="couponPrice" />
      <el-table-column label="图片" align="center" prop="waterImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.waterImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['water:water:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['water:water:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改桶装水信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="名称" prop="waterName">
              <el-input v-model="form.waterName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="类型" prop="waterType">
              <el-radio-group v-model="form.waterType">
                <el-radio
                  v-for="dict in dict.type.water_bucket_type"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="容量" prop="waterCapacity">
              <el-input v-model="form.waterCapacity" placeholder="请输入容量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="售价（元）" prop="waterPrice">
              <el-input type="number" v-model="form.waterPrice" placeholder="请输入售价" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="水票售价（元）" prop="couponPrice">
              <el-input type="number" v-model="form.couponPrice" placeholder="请输入售价" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="图片" prop="waterImage">
              <image-upload limit="1" fileSize="1" v-model="form.waterImage"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWater, getWater, delWater, addWater, updateWater } from "@/api/water/water";

export default {
  name: "Water",
  dicts: ['water_bucket_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 桶装水信息表格数据
      waterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        waterName: null,
        waterType: null,
        waterCapacity: null,
        waterPrice: null,
        couponPrice: null,
        waterImage: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        waterName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        waterType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        waterCapacity: [
          { required: true, message: "容量不能为空", trigger: "blur" }
        ],
        waterPrice: [
          { required: true, message: "售价不能为空", trigger: "blur" }
        ],
        couponPrice: [
          { required: true, message: "水票售价不能为空", trigger: "blur" }
        ],
        waterImage: [
          { required: true, message: "图片不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询桶装水信息列表 */
    getList() {
      this.loading = true;
      listWater(this.queryParams).then(response => {
        this.waterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        waterId: null,
        waterName: null,
        waterType: null,
        waterCapacity: null,
        waterPrice: null,
        waterImage: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.waterId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加桶装水信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const waterId = row.waterId || this.ids
      getWater(waterId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改桶装水信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.waterId != null) {
            updateWater(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWater(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const waterIds = row.waterId || this.ids;
      this.$modal.confirm('是否确认删除桶装水信息编号为"' + waterIds + '"的数据项？').then(function() {
        return delWater(waterIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('water/water/export', {
        ...this.queryParams
      }, `water_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
