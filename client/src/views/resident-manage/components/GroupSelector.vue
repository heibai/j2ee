<template>
  <div class="GroupSelector">
    <!-- 宿舍楼选择 -->
    <div class="selector-item" v-if="selectorData.buildingId !== undefined">
      <span class="label">宿舍楼</span>
      <el-input v-model="selectorData.buildingId"></el-input>
    </div>
    <!-- 宿舍楼选择 -->

    <!-- 房间选择 -->
    <div class="selector-item" v-if="selectorData.roomId !== undefined">
      <span class="label">房间</span>
      <el-input v-model="selectorData.roomId"></el-input>
    </div>

    <el-button
      type="primary"
      round
      @click="handleSearchRoom"
      :disabled="
        selectorData.roomId === null || selectorData.buildingId === null
      "
      >检索
    </el-button>
  </div>
</template>

<script>
import { getManageBuildings } from '@/api/room'
import { getRooms } from '@/api/room'
import { getStudents } from '@/api/user'
export default {
  name: 'GroupSelector',

  data() {
    return {
      selectorData: {
        buildingId: null,
        roomId: null
      },
      groupData: {
        buildingId: null,
        floorId: null,
        roomId: null,
        userId: null
      },
      buildingsData: [],
      floorsData: [],
      roomsData: [],
      usersData: []
    }
  },

  mounted() {
    // this.fetchBuildingsData()
  },
  methods: {
    async fetchBuildingsData() {
      getManageBuildings().then(res => {
        this.buildingsData = res.data.buildings
      })
    },
    async fetchFloorsData() {
      getFloors({ buildingId: this.groupData.buildingId }).then(res => {
        this.floorsData = res.data.floors
      })
    },
    async fetchRoomsData() {
      getRooms({ floorId: this.groupData.floorId }).then(res => {
        this.roomsData = res.data.rooms
      })
    },
    async fetchUsersData() {
      getStudents({ roomId: this.groupData.roomId }).then(res => {
        this.usersData = res.data.users
      })
    },
    handleSearchRoom() {
      this.$emit('searchRoom', this.selectorData)
    }
  }
}
</script>

<style lang="scss" scoped>
.GroupSelector {
  display: flex;
  margin: 0 -10px;
  .selector-item {
    display: flex;
    align-items: center;
    margin: 0 10px;
    span {
      width: 70px;
      margin-right: 10px;
    }
  }
}
</style>
