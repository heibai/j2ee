<template>
  <div id="RoomInfo" class="page-wrapper">
    <!-- 宿舍基础信息 -->
    <h1 class="main-title">
      <span class="mr-gap">宿舍基础信息</span>
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
              <span>
                居住人数： 0
              </span>
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
                type="success"
                size="mini"
                icon="el-icon-money"
                @click="handleEdit(scope.$index, scope.row)"
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
      <RecordTable :table-data="students" />
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

import { getRoomInfo } from '@/api/room'
import { getEvaluates, addEvaluate } from '@/api/evaluate'
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
      students: [],
      selectorData: {
        buildingId: null,
        floorId: null,
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
      }
    }
  },
  mounted() {
    const roomId = this.$route.query.roomId
    if (roomId) {
      this.fetchRoomInfo(roomId)
    }
  },
  created() {
    // 在进入页面是判断角色 如果为住户则生成roomId
    if (this.$store.getter.role === 'resident') {
      // TODO 寻找该用户的住房
    }
  },
  methods: {
    async fetchRoomInfo(roomId) {
      const roomInfo = (await getRoomInfo({ roomId })).data
      this.roomInfo = roomInfo
      this.buildingInfo = roomInfo.building
      this.students = roomInfo.users
      // const evaluates = (await getEvaluates({ roomId: roomInfo.id })).data
      //   .evaluates
      // this.evaluatesData = evaluates
    },
    handleSearchRoom() {
      this.$router.push({
        name: 'roomInfo',
        query: { roomId: this.selectorData.roomId }
      })
    },
    handleSubmit() {
      this.$refs.evaluateForm.validate(result => {
        if (result) {
          addEvaluate({
            note: this.evaluateForm.note,
            score: this.evaluateForm.score,
            roomId: this.roomInfo.id
          }).then(() => {
            this.$message.success('发布成功')
            this.fetchRoomInfo(this.roomInfo.id)
          })
        } else {
          this.$message.error('请填充完整信息')
        }
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
