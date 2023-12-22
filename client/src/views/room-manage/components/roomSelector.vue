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

    <!-- 限制住房人数 -->
    <div class="selector-item">
      <span class="label">限制住房人数</span>
      <el-input v-model="roomData.limitNum"></el-input>
    </div>

    <div class="selector-item">
      <el-button type="primary" round @click="checkAndAddRoom">添加</el-button>
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
import { addRoom, getRoomByRoomIdAndBuildingId } from '@/api/room'
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
        limitNum: null
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
    handleSearchRoom() {
      if (this.validForm()) {
        let params = {
          buildingId: this.roomData.buildingId,
          roomId: this.roomData.roomId
        }
        getRoomByRoomIdAndBuildingId(params)
          .then(res => {
            this.$emit('searched', [res.data])
          })
          .catch(e => {
            console.log(e)
            this.$message({
              type: 'info',
              message: '检索失败'
            })
          })
      }
    },
    // 检查没有重复的房间
    checkAndAddRoom() {
      let params = {
        buildingId: this.roomData.buildingId,
        roomId: this.roomData.roomId
      }
      getRoomByRoomIdAndBuildingId(params)
        .then(res => {
          if (res.data === null) {
            this.addRoom()
          } else {
            this.$message({
              type: 'info',
              message: '该房间已存在'
            })
          }
        })
        .catch(e => {
          console.log(e)
          this.$message({
            type: 'info',
            message: '检索失败'
          })
        })
    },
    addRoom() {
      if (this.validForm()) {
        if (!this.roomData.limitNum || this.roomData.limitNum <= 0)
          return this.$message({
            type: 'error',
            message: '请填写正确信息'
          })
        let params = {
          buildingId: this.roomData.buildingId,
          roomId: this.roomData.roomId,
          limitNum: this.roomData.limitNum,
          status: 1
        }
        addRoom(params)
          .then(res => {
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.$emit('operateFinish')
          })
          .catch(e => {
            console.log(e)
            this.$message({
              type: 'error',
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
