<template>
  <div class="visitor-table">
    <div class="table-title" v-if="title">
      住户管理
    </div>
    <div class="table-wrapper">
      <el-table :data="tableData" style="width: 100%" v-loading="tableLoading">
        <!-- 居住地址 -->
        <el-table-column label="居住地址">
          <template slot-scope="scope">
            {{ scope.row.room.building.name }} {{ scope.row.room.name }}
          </template>
        </el-table-column>
        <el-table-column label="报修原因" prop="name"></el-table-column>
        <!-- <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="身份证" prop="idNumber"></el-table-column>
        <el-table-column label="性别">
          <template slot-scope="scope">
            {{ scope.row.sex === '0' ? '男' : '女' }}
          </template>
        </el-table-column> -->
        <!-- <el-table-column label="到访宿舍楼">
          <template slot-scope="scope">
            {{ scope.row.building.name }}
          </template>
        </el-table-column> -->
        <!-- 维修人 -->
        <el-table-column label="维修人" prop="name"></el-table-column>
        <!-- 联系方式 -->
        <el-table-column label="联系方式" prop="phone"></el-table-column>
        <el-table-column label="报修时间" prop="createdAt">
          <template slot-scope="scope">
            {{ $moment(scope.row.createdAt).format('YYYY-MM-DD HH:mm') }}
          </template>
        </el-table-column>
        <el-table-column label="完成时间" prop="createdAt">
          <template slot-scope="scope">
            {{ $moment(scope.row.createdAt).format('YYYY-MM-DD HH:mm') }}
          </template>
        </el-table-column>
        <!-- 操作区 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VisitorTable',
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
    icon: {
      type: String
    }
  }
}
</script>

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
