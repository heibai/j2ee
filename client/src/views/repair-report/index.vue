<script>
import RecordTable from './components/RecordTable'
import addForm from './components/addForm.vue'
import Pagination from '@/components/Pagination'
import paginationMixins from '@/mixins/paginationMixins'
import { getRepairReportPage } from '@/api/repair-report'
export default {
  name: 'PublicPropertyManage',
  components: {
    RecordTable,
    Pagination,
    addForm
  },
  mixins: [paginationMixins],
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    handleAdd() {
      this.$refs.addForm.show()
    },
    operateFinish() {
      this.getTableData()
    },
    async handlePagination({ page, limit }) {
      this.PageNo = page
      this.PageSize = limit
      this.getTableData()
    },
    async getTableData() {
      const params = {
        PageNo: this.PageNo,
        PageSize: this.PageSize
      }

      const { data } = await getRepairReportPage(params)
      this.tableData = data.records
      this.count = data.total

      console.log(this.tableData)
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="wrapper"></div>
    <!-- 管理概览 -->
    <!-- 公共财产管理 -->
    <h1 class="main-title">
      <span>
        报修管理
      </span>
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
        新增报修
      </el-button>
    </h1>

    <!-- 新增按钮 -->
    <div class="operation-container">
      <div class="wrapper">
        <RecordTable
          type="public"
          :table-data="tableData"
          :show-pagination="false"
          @operateFinish="operateFinish"
        >
        </RecordTable>
        <Pagination
          :total="count"
          :page="PageNo"
          @pagination="handlePagination"
          :hidden="tableData.length === 0"
        />
      </div>
    </div>
    <addForm @operateFinish="operateFinish" ref="addForm"></addForm>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding: 0px 60px 0px;
  .main-title {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .operation-container {
    margin: 20px 0;
  }

  .wrapper {
    border-radius: 5px;
    overflow: hidden;
    margin: 40px 0;
  }
}
</style>
