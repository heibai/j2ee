<template>
  <div id="admin-manage" class="page-wrapper">
    <!-- 添加管理员 -->
    <h1 class="main-title">添加工作人员</h1>
    <div class="wrapper top-wrapper">
      <div class="main-card right">
        <AddAdminForm />
      </div>
    </div>
    <!-- 添加管理员 -->

    <!-- 表格 -->
    <h1 class="main-title">人员列表</h1>
    <div class="wrapper main-card">
      <AdminTable :tableData="adminsTableData" />

      <Pagination
        :total="count"
        :page="PageNo"
        @pagination="handlePagination"
        :hidden="adminsTableData.length === 0"
      />
    </div>
    <!-- 表格 -->
  </div>
</template>

<script>
import AddAdminForm from './components/AddAdminForm'
import AdminTable from './components/AdminTable'
import Pagination from '@/components/Pagination'
import { getUserList } from '@/api/user'
import paginationMixins from '@/mixins/paginationMixins'
export default {
  name: 'adminManage',
  components: {
    AddAdminForm,
    AdminTable,
    Pagination
  },
  mixins: [paginationMixins],
  data() {
    return {
      adminsTableData: [],
      total: 0,
      superAdminCount: 0,
      adminCount: 0
    }
  },
  mounted() {
    this.fetchTableData()
  },
  methods: {
    async handlePagination({ page, limit }) {
      this.PageNo = page
      this.PageSize = limit
      this.fetchTableData()
    },
    async fetchTableData() {
      //先查询管理员 再查询物业人员
      const params = {
        PageNo: this.PageNo,
        PageSize: this.PageSize
      }

      const { data } = await getUserList(params)
      this.adminsTableData = data.records
      this.count = data.total
    }
  }
}
</script>

<style lang="scss" scoped>
.wrapper {
  margin: 40px 0;
}
.top-wrapper {
  display: flex;
  > .left {
    margin-right: 20px;
    width: 500px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    .title-wrapper {
      font-size: 28px;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .admin-card {
      padding: 20px;
      border-radius: 5px;
      border: 1px solid rgba($color: #000000, $alpha: 0.2);
      color: #ffffff;
    }
    .admin {
      margin-bottom: 20px;
      background-color: $color-primary;
    }
    .superAdmin {
      background-color: $color-origin;
    }
  }
  > .right {
    width: 100%;
    overflow: visible;
  }
}
</style>
