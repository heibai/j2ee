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
  </div>
</template>

<script>
import { getManageBuildings } from '@/api/room'
import { getRooms } from '@/api/room'
import { getStudents } from '@/api/user'
export default {
  name: 'GroupSelector',
  model: {
    prop: 'selectorData',
    event: 'selected'
  },
  data() {
    return {}
  },
  props: {
    selectorData: {
      type: Object,
      required: true
    }
  },

  mounted() {
    // this.fetchBuildingsData()
  },
  methods: {}
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
