<template>
  <div id="visitor-manage" class="page-wrapper">
    <!-- 选择器 -->
    <h1 class="main-title">选择学生</h1>
    <div class="wrapper">
      <el-tabs type="border-card">
        <el-tab-pane label="级联选择">
          <GroupSelector :selectorData="selectorData" />
          <el-button
            type="primary"
            @click="fetchUserInfo('id', selectorData.userId)"
            >搜索</el-button
          >
        </el-tab-pane>
        <el-tab-pane label="按学号搜索">
          <StudentSearcher v-model="searchContent" />
          <el-button
            type="primary"
            @click="fetchUserInfo('account', searchContent)"
            >搜索</el-button
          >
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 选择器 -->
    <h1 class="main-title">
      <span>住户管理</span>
      <el-button type="primary" @click="onAddBtnClick">新增</el-button>
    </h1>
    <div class="main-card wrapper">
      <VisitorTable
        :tableData="tableData"
        type="back"
        :table-loading="tableLoading"
      />
      <Pagination
        :total="count"
        :page="current"
        @pagination="handlePagination"
        :hidden="tableData.length === 0"
      />
    </div>
    <VisitorCreateModal
      :visible.sync="createModalVisible"
      @create-success="onCreateSuccess"
    />
  </div>
</template>

<script>
import VisitorTable from './components/VisitorTable.vue'
import Pagination from '@/components/Pagination'
import VisitorCreateModal from './components/VisitorCreateModal.vue'
import GroupSelector from '@/components/GroupSelector'
import StudentSearcher from './components/StudentSearcher'
import { getVisitorList } from '@/api/visitor'

export default {
  components: {
    VisitorTable,
    Pagination,
    VisitorCreateModal,
    GroupSelector,
    StudentSearcher
  },
  data() {
    return {
      current: 1,
      count: 0,
      step: 10,
      tableData: [],
      tableLoading: false,
      createModalVisible: false
    }
  },
  methods: {
    fetchTableData() {
      const params = {
        current: this.current,
        step: this.step
      }
      this.tableLoading = true
      getVisitorList(params)
        .then(res => {
          this.tableData = res.data.rows
          this.count = res.data.count
        })
        .finally(() => {
          this.tableLoading = false
        })
    },
    handlePagination({ page, limit }) {
      this.current = page
      this.step = limit
      this.fetchTableData()
    },
    onAddBtnClick() {
      this.createModalVisible = true
    },
    onCreateSuccess() {
      this.current = 1
      this.fetchTableData()
    }
  },
  mounted() {
    this.fetchTableData()
  }
}
</script>

<style lang="scss" scoped>
.main-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.wrapper {
  margin: 40px 0;
}
</style>
