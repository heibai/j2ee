<template>
  <div class="GroupSelector">
    <!-- 宿舍楼选择 -->
    <div class="selector-item">
      <span class="label">宿舍楼</span>
      <el-input v-model="roomData.buildingId"></el-input>
    </div>
    <!-- 宿舍楼选择 -->

    <!-- 房间选择 -->
    <div class="selector-item">
      <span class="label">房间</span>
      <el-input v-model="roomData.roomId"></el-input>
    </div>

    <div class="selector-item">
      <el-button type="primary" round @click="addRoom">添加</el-button>
    </div>

    <el-button
      type="primary"
      round
      @click="handleSearchRoom"
      :disabled="selectorData.roomId === null"
      >检索</el-button
    >
  </div>
</template>
<script>
import { addRoom } from '@/api/room'
export default {
  name: 'GroupSelector',
  model: {
    prop: 'selectorData',
    event: 'selected'
  },
  data() {
    return {
      roomData: {
        buildingId: null,
        roomId: null,
        hadNum: 0,
        limitNum: 10
      },
      buildingsData: [],
      floorsData: [],
      roomsData: [],
      usersData: []
    }
  },
  props: {
    selectorData: {
      type: Object,
      required: true
    }
  },

  mounted() {},
  methods: {
    validForm() {
      if (this.roomData.buildingId && this.roomData.roomId) {
        return true
      }
      this.$message({
        type: 'error',
        message: '请填写完整信息'
      })
      return false
    },
    addRoom() {
      if (this.validForm()) {
        addRoom(this.roomData)
          .then(res => {
            this.$message({
              type: 'success',
              message: '添加成功!'
            })
            this.$emit('operateFinish')
          })
          .catch(e => {
            console.log(e)
            this.$message({
              type: 'info',
              message: '添加失败'
            })
          })
      }
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
      margin-right: 10px;
      width: 90px;
    }
  }
}
</style>
