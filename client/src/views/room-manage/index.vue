<script>
import RecordTable from './components/RecordTable'
import roomSelector from './components/roomSelector.vue'
import Pagination from '@/components/Pagination'
import paginationMixins from '@/mixins/paginationMixins'

import { getRoomPage } from '@/api/room'
export default {
  name: 'PublicPropertyManage',
  components: {
    RecordTable,
    roomSelector,
    Pagination
  },
  mixins: [paginationMixins],
  data() {
    return {
      tableData: [],
      selectorData: {}
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    handleAdd() {
      this.$refs.addForm.show()
    },
    handleSearchRoom() {},
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
      const { data } = await getRoomPage(params)
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
    <!-- 宿舍基础信息 -->
    <div class="wrapper main-card selector-wrapper">
      <roomSelector
        :selectorData="selectorData"
        @operateFinish="operateFinish"
      />
    </div>
    <h1 class="main-title">
      <span>
        住房管理
      </span>
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
