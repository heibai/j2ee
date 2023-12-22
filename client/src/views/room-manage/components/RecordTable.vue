<script>
import { deleteRoom } from '@/api/room'
export default {
  name: 'RecordTable',
  components: {},
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
    handleEdit(index, row) {
      let formData = JSON.parse(JSON.stringify(row))
      // 去住房信息中
      this.$router.push({
        path: '/roomInfo/index',
        query: {
          roomId: row.id
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
          await deleteRoom(row)
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
        <el-table-column prop="buildingId" label="楼栋"> </el-table-column>

        <!-- 信息 -->
        <el-table-column prop="roomId" label="房间号"> </el-table-column>

        <!-- 限制人数 -->
        <el-table-column prop="limitNum" label="限制人数"> </el-table-column>

        <el-table-column prop="hadNum" label="已入住人数"> </el-table-column>

        <!-- 操作区 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
              >详情
            </el-button>
            <el-button
              type="danger"
              size="mini"
              :disabled="scope.row.hadNum != 0"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
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
