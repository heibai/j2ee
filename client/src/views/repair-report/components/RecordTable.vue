<script>
import detailForm from './detailForm.vue'
import { deleteRepairReport, updateRepairReport } from '@/api/repair-report'
export default {
  name: 'RecordTable',
  components: {
    detailForm
  },
  data() {
    return {}
  },
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    tableLoading: {
      type: Boolean,
      default: false
    },
    title: {
      type: String
    },
    type: {
      type: String,
      required: true
    },
    icon: {
      type: String
    }
  },
  computed: {},
  methods: {
    handleDetail(index, row) {
      this.$refs.detailForm.show(row)
    },
    handleTakeUp(index, row) {
      this.updateRepair(index, row, 2)
    },
    handlerResolve(index, row) {
      this.updateRepair(index, row, 3)
    },
    updateRepair(index, row, status) {
      let formData = JSON.parse(JSON.stringify(row))
      formData.repairerId = this.$store.getters.userInfo.id
      formData.status = status
      let tip = formData.status == 2 ? '受理' : '解决'
      console.log(status)
      if (formData.status == 3) {
        // 需要 YYYY-MM-DD HH:mm:ss
        formData.repairTime = this.$moment().format('YYYY-MM-DD HH:mm:ss')
      }
      // 确认受理
      this.$confirm(`确认${tip}该报修？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          console.log(formData)

          await updateRepairReport(formData)
          this.$message({
            type: 'success',
            message: `${tip}成功!`
          })
          this.$emit('operateFinish')
        })
        .catch(() => {})
    },

    handleDelete(index, row) {
      let formData = JSON.parse(JSON.stringify(row))
      formData.status = 4

      // 确认删除
      this.$confirm('确认删除该报修？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          await deleteRepairReport(formData)
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.$emit('operateFinish')
        })
        .catch(() => {})
    }
  }
}
</script>

<template>
  <div class="record-table">
    <div class="table-title" v-if="title">
      <i v-if="icon" class="icon" :class="icon"></i> {{ title }}
    </div>
    <div class="table-wrapper">
      <el-table :data="tableData" style="width: 100%" v-loading="tableLoading">
        <!-- 财产物品名 -->
        <el-table-column prop="reportId" label="报修人"></el-table-column>

        <el-table-column prop="repairerId" label="受理人"></el-table-column>
        <!-- 信息 -->
        <el-table-column prop="message" label="报修信息"> </el-table-column>

        <!-- 投诉时间 -->
        <el-table-column prop="replyTime" label="报修时间"> </el-table-column>

        <!-- 回应时间 -->
        <el-table-column prop="time" label="修复时间"> </el-table-column>
        <!-- 状态  1报修中 2 已受理 3 已修复-->
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <!-- el-tag -->
            <el-tag v-if="scope.row.status == 1" type="danger" size="mini">
              报修中
            </el-tag>
            <el-tag v-if="scope.row.status == 2" type="warning" size="mini">
              已受理
            </el-tag>
            <el-tag v-if="scope.row.status == 3" type="success" size="mini">
              已修复
            </el-tag>
          </template>
        </el-table-column>

        <!-- 操作区 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 详情 -->
            <el-button
              type="primary"
              size="mini"
              @click="handleDetail(scope.$index, scope.row)"
              >详情
            </el-button>

            <el-button
              v-if="scope.row.status == 1"
              type="primary"
              size="mini"
              @click="handleTakeUp(scope.$index, scope.row)"
              v-has="'superAdmin,worker'"
              >受理
            </el-button>

            <el-button
              v-if="scope.row.status == 2"
              type="primary"
              size="mini"
              @click="handlerResolve(scope.$index, scope.row)"
              v-has="'superAdmin,worker'"
              >解决
            </el-button>

            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.$index, scope.row)"
              v-has="'superAdmin,resident'"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <detailForm ref="detailForm" @operateFinish="$emit('operateFinish')">
    </detailForm>
  </div>
</template>

<style lang="scss" scoped>
.table-title {
  background-color: #fff;
  padding: 20px;
  padding-bottom: 0px;
  font-size: 18px;
  font-weight: bold;
  color: rgba($color: #000000, $alpha: 0.5);
  display: flex;
  align-items: center;
  .icon {
    font-size: 24px;
    margin-right: 10px;
  }
}
.table-wrapper {
  padding-left: 20px;
  background-color: #fff;
  .bdge-wrapp {
    display: flex;
    justify-content: center;
    .early-bdge {
      height: 10px;
      width: 10px;
      border-radius: 50%;
      background-color: $color-danger;
    }
    .early-bdge-green {
      background-color: $color-success;
    }
  }
}
a {
  color: $color-primary;
  &:hover {
    text-decoration: underline;
  }
}
</style>
