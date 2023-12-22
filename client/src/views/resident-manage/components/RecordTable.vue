<script>
import { deleteResident } from '@/api/resident'
import { updateUserInfo } from '@/api/user'
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
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该住户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          await deleteResident(row)

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
    },
    updateUserInfo() {
      let reqData = {
        id: row.userId,
        role: 'visitor'
      }
      updateUserInfo(reqData).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '更新用户信息成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: '更新用户信息失败'
          })
        }
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
        <el-table-column prop="userId" label="入住人">
          <template slot-scope="scope">
            {{ scope.row.name }}
          </template>
        </el-table-column>

        <!-- 信息 -->
        <el-table-column prop="message" label="入住人电话">
          <template slot-scope="scope">
            {{ scope.row.userId }}
          </template>
        </el-table-column>

        <!-- 投诉时间 -->
        <el-table-column prop="buildingId" label="楼栋">
          <template slot-scope="scope">
            {{ scope.row.buildingId }}
          </template></el-table-column
        >

        <!-- 回应时间 -->
        <el-table-column prop="roomId" label="房间号">
          <template slot-scope="scope">
            {{ scope.row.roomId }}
          </template>
        </el-table-column>

        <!-- 操作区 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.$index, scope.row)"
              >退户</el-button
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
