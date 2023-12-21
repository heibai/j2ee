<script>
import RecordTable from './components/RecordTable'
import addProperty from './components/addProperty.vue'
import Pagination from '@/components/Pagination'
import paginationMixins from '@/mixins/paginationMixins'
import { getProperty } from '@/api/property'
export default {
  name: 'PublicPropertyManage',
  components: {
    RecordTable,
    Pagination,
    addProperty
  },
  mixins: [paginationMixins],
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.getPropertyTableData()
  },
  methods: {
    handleAdd() {
      this.$refs.addProperty.show()
    },

    operateFinish() {
      this.getPropertyTableData()
    },
    async handlePagination({ page, limit }) {
      this.PageNo = page
      this.PageSize = limit
      this.getPropertyTableData()
    },
    async getPropertyTableData() {
      const params = {
        PageNo: this.PageNo,
        PageSize: this.PageSize
      }

      const { data } = await getProperty(params)
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
        公共财产管理
      </span>
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
        新增公共财产
      </el-button>
    </h1>

    <!-- 新增按钮 -->
    <div class="operation-container">
      <div class="wrapper">
        <RecordTable
          type="public"
          title="公共财产"
          icon="el-icon-suitcase"
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
    <addProperty @operateFinish="operateFinish" ref="addProperty"></addProperty>
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
