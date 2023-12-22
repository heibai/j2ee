<template>
  <div id="UserInfo" class="page-wrapper">
    <h1 class="main-title">
      <span>
        住户管理
      </span>
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
        新增住户
      </el-button>
    </h1>

    <div v-has="'superAdmin'" class="wrapper main-card selector-wrapper">
      <GroupSelector
        :selectorData="selectorData"
        @searchRoom="searchRoomResident"
      />
    </div>

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
import GroupSelector from './components/GroupSelector'
import StudentSearcher from './components/StudentSearcher'
import Pagination from '@/components/Pagination'
import paginationMixins from '@/mixins/paginationMixins'
import RecordTable from './components/RecordTable'
import addForm from './components/addForm'
import { getResidentPage } from '@/api/resident'
import { getRoomByRoomIdAndBuildingId } from '@/api/room'
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
    searchRoomResident(selectorData) {
      getRoomByRoomIdAndBuildingId(selectorData).then(res => {
        if (res.data === null) {
          this.$message.error('该住房不存在')
          return
        }
        this.getTableData(res.data.id)
      })
    },
    async getTableData(roomId = null) {
      const params = {
        PageNo: this.PageNo,
        PageSize: this.PageSize,
        roomId
      }
      const { data } = await getResidentPage(params)
      this.tableData = this.generationTableData(data.records)
      this.count = data.total
      console.log(this.tableData)
    },
    generationTableData(records) {
      return records.map(record => {
        console.log(record)
        let resident = record[0]
        let user = record[1]
        let room = record[2]
        return {
          ...resident,
          name: user.name,
          account: user.userId,
          buildingId: room.buildingId,
          roomId: room.roomId
        }
      })
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
.selector-wrapper {
  display: flex;
  justify-content: space-between;
}
</style>
