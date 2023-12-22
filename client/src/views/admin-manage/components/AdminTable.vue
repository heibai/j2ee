<template>
  <div calss="AdminTable">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" label="姓名"> </el-table-column>
      <el-table-column prop="userId" label="手机号"> </el-table-column>
      <el-table-column label="身份">
        <template slot-scope="scope">
          <span class="superAdmin" v-if="scope.row.role === 'superAdmin'">{{
            transRoleString(scope.row.role)
          }}</span
          ><span class="admin" v-else-if="scope.row.role === 'worker'">{{
            transRoleString(scope.row.role)
          }}</span>
          <span v-else>{{ transRoleString(scope.row.role) }}</span>
        </template>
      </el-table-column>
      <!-- 操作区 -->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            :disabled="$store.getters.role !== 'superAdmin'"
            type="danger"
            size="mini"
            @click="handleDelete(scope.$index, scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { deleteUser } from '@/api/user'
import { transRoleString } from '@/filters'
export default {
  name: 'AdminTable',
  data() {
    return {}
  },
  props: {
    tableData: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    transRoleString,
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该工作人员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          // 只有管理员能删除角色且不能删除自己
          if (
            row.role === 'superAdmin' &&
            row.userId === this.$store.state.userId
          ) {
            this.$message({
              type: 'error',
              message: '您不能删除自己!'
            })
            return
          }
          const res = await deleteUser(row)
          if (res.code === 200) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.tableData.splice(index, 1)
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.admin {
  color: $color-primary;
}
.superAdmin {
  color: $color-origin;
}
.room-wrap {
  display: flex;
  flex-wrap: wrap;
  margin: 0 -10px;
  span {
    display: block;
    margin: 5px 10px;
  }
}
.all-room {
  color: rgba($color: #000000, $alpha: 0.3);
}
</style>
