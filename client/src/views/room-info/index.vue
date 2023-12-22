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
              <span> 欠费 ：{{ fees }} 元 </span>
              <!-- 缴费 -->
              <el-button
                v-has="'superAdmin'"
                type="success"
                size="mini"
                icon="el-icon-money"
                @click="payRoomFees"
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
import RecordTable from './components/recordTable'
import RoomInfoEditModal from './components/RoomInfoEditModal.vue'
import {
  getRoomInfo,
  getRoomUsers,
  getRoomByRoomIdAndBuildingId
} from '@/api/room'
import { getRoomFees, payRoomFees } from '@/api/fees'
export default {
  name: 'RoomInfo',
  components: {
    GroupSelector,
    RecordTable,
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
      evaluateForm: {
        note: '',
        score: 60
      },
      fees: 0,
      editModalVisible: false
    }
  },
  watch: {
    '$route.query.roomId': async function(newVal) {
      if (newVal && this.$route.name === 'roomInfo') {
        await this.fetchRoomInfo(newVal)
        await this.fetchRoomResident(newVal)
        await this.fetchRoomFees(newVal)
      }
    }
  },
  async mounted() {
    let roomId = this.$route.query.roomId
    if (roomId) {
      await this.fetchRoomInfo(roomId)
      await this.fetchRoomResident(roomId)
      await this.fetchRoomFees(roomId)
    }
    console.log(this.roomInfo)
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

    async fetchRoomFees(roomId) {
      const roomFees = (await getRoomFees({ roomId })).data
      this.fees = roomFees
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

    payRoomFees() {
      // 一键缴费
      this.$confirm('此操作将帮住房缴纳所有欠费, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          await payRoomFees({ roomId: this.roomInfo.id })
          this.$message({
            type: 'success',
            message: '缴费成功!'
          })
          await this.fetchRoomFees(this.roomInfo.id)
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
