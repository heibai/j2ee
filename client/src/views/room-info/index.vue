<template>
  <div id="RoomInfo" class="page-wrapper">
    <!-- 宿舍基础信息 -->
    <h1 class="main-title">
      <span class="mr-gap">住房基础信息</span>
    </h1>
    <div v-has="'superAdmin'" class="wrapper main-card selector-wrapper">
      <GroupSelector :selectorData="selectorData" />
      <el-button
        type="primary"
        round
        @click="handleSearchRoom"
        :disabled="selectorData.roomId === null"
        >检索住房</el-button
      >
    </div>

    <div class="detail-wrapper">
      <el-row :gutter="12">
        <el-col :span="12">
          <el-card shadow="hover">
            <div class="detail-info">
              <span> 居住人数： {{ roomInfo.hadNum }} </span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <div class="detail-info">
              <span>
                欠费 ： 0
              </span>
              <!-- 缴费 -->
              <el-button
                v-has="'superAdmin'"
                type="success"
                size="mini"
                icon="el-icon-money"
                @click="handleSubmit()"
              >
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 入住成员 -->
    <h1 class="main-title">入住成员</h1>
    <div class="wrapper main-card">
      <RecordTable :table-data="tableData" />
    </div>
    <!-- 宿舍成员 -->
  </div>
</template>

<script>
import GroupSelector from './components/GroupSelector'
import PanelGroup from './components/PanelGroup'
import RecordTable from './components/recordTable'
import Evaluates from '../dashboard/student/components/Evaluates'
import RoomInfoEditModal from './components/RoomInfoEditModal.vue'
import {
  getRoomInfo,
  getRoomUsers,
  getRoomByRoomIdAndBuildingId
} from '@/api/room'
export default {
  name: 'RoomInfo',
  components: {
    GroupSelector,
    PanelGroup,
    RecordTable,
    Evaluates,
    RoomInfoEditModal
  },
  data() {
    return {
      roomInfo: {},
      buildingInfo: {},
      floorInfo: {},
      tableData: [],
      selectorData: {
        buildingId: null,
        roomId: null
      },
      evaluatesData: [],
      evaluateForm: {
        note: '',
        score: 60
      },
      editModalVisible: false
    }
  },
  watch: {
    '$route.query.roomId': function(newVal) {
      if (newVal && this.$route.name === 'roomInfo') {
        this.fetchRoomInfo(newVal)
        this.fetchRoomResident(newVal)
      }
    }
  },
  mounted() {
    let roomId = this.$route.query.roomId
    if (roomId) {
      this.fetchRoomInfo(roomId)
      this.fetchRoomResident(roomId)
    }
  },
  created() {
    // 在进入页面是判断角色 如果为住户则生成roomId
    let role = this.$store.getters.role
    if (role === 'resident') {
      // TODO 寻找该用户的住房
      this.$router.push({
        name: 'roomInfo',
        query: { roomId: this.$store.getters.room }
      })
    }
  },
  methods: {
    async fetchRoomInfo(roomId) {
      const roomInfo = (await getRoomInfo({ id: roomId })).data

      this.roomInfo = roomInfo
    },
    async fetchRoomResident(roomId) {
      const roomUsers = (await getRoomUsers({ roomId })).data
      this.tableData = roomUsers
    },
    async handleSearchRoom() {
      const roomInfo = (
        await getRoomByRoomIdAndBuildingId({
          roomId: this.selectorData.roomId,
          buildingId: this.selectorData.buildingId
        })
      ).data
      if (roomInfo === null) {
        this.$message({
          type: 'error',
          message: '未找到该房间'
        })
        return
      }
      this.roomInfo = roomInfo
      this.$router.push({
        name: 'roomInfo',
        query: { roomId: this.roomInfo.id }
      })
    },

    handleSubmit() {
      // 一键缴费
      this.$confirm('此操作将帮用户缴纳所有欠费, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          await deleteComplaint(row)
          this.$message({
            type: 'success',
            message: '缴费成功!'
          })
          this.$emit('operateFinish')
        })
        .catch(e => {
          console.log(e)
          this.$message({
            type: 'info',
            message: '已取消缴费'
          })
        })
    }
  }
}
</script>

<style lang="scss" scoped>
#RoomInfo {
  > .wrapper {
    margin: 40px 0;
  }

  > .detail-wrapper {
    margin: 40px 0;
    .detail-info {
      display: flex;
      justify-content: space-between;
      align-items: center;
      > span {
        height: 26px;
      }
    }
  }
  .selector-wrapper {
    display: flex;
    justify-content: space-between;
  }
  .form-wrapper {
    margin-bottom: 40px;
    .btn-wrapper {
      display: flex;
      flex-direction: row-reverse;
    }
  }
  .mr-gap {
    margin-right: 16px;
  }
}
</style>
