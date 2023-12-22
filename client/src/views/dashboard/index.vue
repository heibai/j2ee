<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import studentDashboard from './student'
import RecordTable from './components/RecordTable.vue'
export default {
  name: 'Dashboard',
  components: { adminDashboard, studentDashboard, RecordTable },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters(['roles'])
  },
  created() {
    // if (this.roles.includes('student')) {
    //   this.currentRole = 'studentDashboard'
    // }
  },
  methods: {}
}
</script>

<template>
  <div class="dashboard-container">
    <!-- <component :is="currentRole" /> -->

    <div class="wrapper">
      <!-- <PanelGroup :building-id="buildingId"></PanelGroup> -->
    </div>
    <!-- 管理概览 -->
    <!-- 公共财产管理 -->
    <h1 class="main-title">暨云小区</h1>
    <div class="wrapper">
      <RecordTable
        type="public"
        title="公共财产"
        icon="el-icon-suitcase"
        :table-data="[]"
        :show-pagination="false"
      ></RecordTable>
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
