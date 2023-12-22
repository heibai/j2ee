<script>
import { mapGetters } from 'vuex'
import RecordTable from './components/RecordTable.vue'
import Pagination from '@/components/Pagination'
import PanelGroup from './components/PanelGroup'
import paginationMixins from '@/mixins/paginationMixins'
import { getProperty } from '@/api/property'

export default {
  name: 'Dashboard',
  components: { RecordTable, PanelGroup, Pagination },
  data() {
    return {
      currentRole: 'adminDashboard',
      tableData: []
    }
  },
  mixins: [paginationMixins],
  computed: {
    ...mapGetters(['roles'])
  },
  created() {
    this.getPropertyTableData()
  },
  methods: {
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
  <div class="dashboard-container">
    <!-- <component :is="currentRole" /> -->
    <h1 class="main-title">暨云小区</h1>

    <div class="wrapper">
      <PanelGroup></PanelGroup>
    </div>
    <!-- 管理概览 -->
    <!-- 公共财产管理 -->
    <div class="wrapper">
      <RecordTable
        type="public"
        title="公共财产"
        icon="el-icon-suitcase"
        :tableData="tableData"
        :show-pagination="false"
      ></RecordTable>
      <Pagination
        :total="count"
        :page="PageNo"
        @pagination="handlePagination"
        :hidden="tableData.length === 0"
      />
    </div>

    <!-- <h1 class="main-title">最新公告</h1> -->
    <!-- 最新学生早起情况 -->
    <!-- <div class="wrapper">
      <RecordTable
        type="getup"
        title="早起动态"
        icon="el-icon-alarm-clock"
        :table-data="getupTableData"
        :show-pagination="false"
      ></RecordTable>
    </div> -->

    <!-- 最新学生早起情况 -->
  </div>
</template>

<style lang="scss" scoped>
.dashboard-container {
  padding: 0px 60px 0px;
  .main-title {
    margin-top: 20px;
    display: flex;
    align-items: center;
  }
  .wrapper {
    border-radius: 5px;
    overflow: hidden;
    margin: 40px 0;
  }
}
</style>
