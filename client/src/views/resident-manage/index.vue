<template>
  <div id="UserInfo" class="page-wrapper">
    <!-- 选择器 -->
    <!-- <h1 class="main-title">选择学生</h1> -->
    <!-- <div class="wrapper">
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
    </div> -->
    <!-- 选择器 -->

    <h1 class="main-title">
      <span>
        住户管理
      </span>
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
        新增住户
      </el-button>
    </h1>
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

<script>
import GroupSelector from '@/components/GroupSelector'
import StudentSearcher from './components/StudentSearcher'
import Pagination from '@/components/Pagination'
import paginationMixins from '@/mixins/paginationMixins'
import RecordTable from './components/RecordTable'
import addForm from './components/addForm'
import { getResidentPage } from '@/api/resident'
export default {
  name: 'UserInfo',
  components: {
    GroupSelector,
    StudentSearcher,
    Pagination,
    addForm,
    RecordTable
  },
  mixins: [paginationMixins],
  data() {
    return {
      selectorData: {
        buildingId: null,
        floorId: null,
        roomId: null,
        userId: null
      },
      tableData: [],
      searchContent: '',
      studentInfo: {},
      editModalVisible: false
    }
  },
  provide() {
    return {
      operateFinish: this.operateFinish
    }
  },
  methods: {
    handleAdd() {
      this.$refs.addForm.show()
    },

    operateFinish() {
      this.$refs.addForm.hidden()
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
      const { data } = await getResidentPage(params)
      this.tableData = data.records
      this.count = data.total
      console.log(this.tableData)
    }
  },
  mounted() {
    this.getTableData()
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
.el-tab-pane {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
::v-deep .el-tabs__content {
  padding: 40px 20px;
  width: 100%;
}

.userInfo-wrapper {
  .top {
    .roomInfo-card {
      background-color: #fff;
      padding: 30px;
    }
    .userInfo-card {
      background-color: #fff;
      padding: 30px;
      height: 223px;
      box-sizing: content-box;
      .title {
        font-weight: bold;
        color: $color-primary;
        font-size: 22px;
        margin-bottom: 20px;
        display: flex;
        align-items: center;
        justify-content: space-between;
      }
      .info-item {
        margin: 20px 0;
      }
      :nth-child(3) {
        margin-top: 40px;
      }
    }
  }
  .bottom {
    .process-item {
      span {
        display: block;
        margin: 10px 0;
      }
    }
    :first-child span {
      margin-top: 0px;
    }
  }
}
</style>
