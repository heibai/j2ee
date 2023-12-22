<script>
import editForm from './editForm.vue'
import { deleteFees, updateFees } from '@/api/fees'
import { formatDataTime } from '@/filters'
export default {
  name: 'RecordTable',
  components: {
    editForm
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
    formatDataTime,
    handleEdit(index, row) {
      let formData = JSON.parse(JSON.stringify(row))
      formData.status = 2
      // 缴费
      this.$confirm('此操作将缴费, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          let res = await updateFees(formData)
          this.$message({
            type: 'success',
            message: '缴费成功!'
          })
          this.$emit('operateFinish')
        })
        .catch(e => {
          console.log(e)
          this.$message({
            type: 'info',
            message: '已取消缴费'
          })
        })
    },
    toRoomDetail(index, row) {
      this.$router.push({
        path: '/roomInfo/index',
        query: {
          roomId: row.roomId
        }
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          await deleteFees(row)
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.$emit('operateFinish')
        })
        .catch(e => {
          console.log(e)
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
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
        <el-table-column prop="roomId" label="房间号"> </el-table-column>

        <!-- 信息 -->
        <el-table-column prop="price" label="费用"> </el-table-column>

        <el-table-column prop="type" label="费用类型">
          <template slot-scope="scope">
            <!-- 为0表示已删除，为1表示待缴费，为2表示已缴费，为3表示已过期 -->
            <el-tag type="danger" v-if="scope.row.type == 1">物业费</el-tag>
            <el-tag type="warning" v-else-if="scope.row.type == 2"
              >水电费
            </el-tag>
            <el-tag type="danger" v-else>无</el-tag>
          </template>
        </el-table-column>

        <!-- 状态 -->
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <!-- 为0表示已删除，为1表示待缴费，为2表示已缴费，为3表示已过期 -->
            <el-tag type="warning" v-if="scope.row.status == 1">待缴费</el-tag>
            <el-tag type="success" v-else-if="scope.row.status == 2"
              >已缴费</el-tag
            >
            <el-tag type="danger" v-else-if="scope.row.status == 3"
              >已过期</el-tag
            >
            <el-tag type="danger" v-else>无</el-tag>
          </template>
        </el-table-column>

        <!-- 缴费时间 -->
        <el-table-column prop="time" label="缴费时间">
          <template slot-scope="scope">
            {{ scope.row.time ? formatDataTime(scope.row.time) : 'N/A' }}
          </template>
        </el-table-column>

        <el-table-column prop="deadline" label="截止时间">
          <template slot-scope="scope">
            {{
              scope.row.deadline ? formatDataTime(scope.row.deadline) : 'N/A'
            }}
          </template>
        </el-table-column>

        <!-- 回应时间 -->
        <!-- <el-table-column prop="replyTime" label="回应时间"> </el-table-column> -->

        <!-- 操作区 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="toRoomDetail(scope.$index, scope.row)"
              >住户详情
            </el-button>
            <!-- 缴费 -->
            <el-button
              type="success"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
              >缴费
            </el-button>
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.$index, scope.row)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <editForm ref="editForm" @operateFinish="$emit('operateFinish')">
    </editForm>
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
