<script>
import paginationMixins from '@/mixins/paginationMixins'
import Pagination from '@/components/Pagination'
import { createResident } from '@/api/resident'
import { getRoomPageAvailable } from '@/api/room'
export default {
  components: {
    Pagination
  },
  mixins: [paginationMixins],
  inject: ['operateFinish'],
  props: {
    visible: { type: Boolean, default: false }
  },
  data() {
    return {
      selectorData: {
        buildingId: '',
        roomId: ''
      },

      roomTable: [],
      tableLoading: false,
      selectRows: [] // 当前行
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(rows) {
      this.selectRows = rows
    },
    handlePagination({ page, limit }) {
      this.PageNo = page
      this.PageSize = limit
      this.getTableData()
    },
    getTableData() {
      this.tableLoading = true
      let reqData = {
        ...this.selectorData,
        pageNo: this.PageNo,
        pageSize: this.PageSize
      }
      getRoomPageAvailable(reqData).then(res => {
        console.log(res)
        if (res.code === 200) {
          console.log(res.data)
          this.roomTable = res.data
          this.count = 0
          // this.count = res.data.total
        }
        this.tableLoading = false
      })
    },
    checkIn() {
      console.log()
      let reqData = {
        userId: this.checkInUser.id,
        ...this.selectorData
      }
      console.log(reqData)
      // TODO入住逻辑
      createResident(reqData).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '发布成功'
          })
          this.$emit('update:visible', false)
          this.operateFinish()
        } else {
          this.$message({
            type: 'error',
            message: '入住失败'
          })
        }
      })
    }
  }
}
</script>
<template>
  <el-dialog
    width="30%"
    title="内层 Dialog"
    :visible.sync="visible"
    @update:visible="$emit('update:visible', $event)"
    append-to-body
    fullscreen
  >
    <div>
      <!-- 搜索栏 -->
      <div class="search-container">
        <!-- 楼栋 房间号 -->
        <el-form :inline="true" :model="selectorData" class="search-form">
          <el-form-item label="楼栋">
            <el-input v-model="selectorData.buildingId"></el-input>
          </el-form-item>

          <el-form-item label="房间号">
            <el-input v-model="selectorData.roomId"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="">查询</el-button>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="checkIn">发布</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="table-wrapper">
        <el-table
          ref="multipleTable"
          :data="roomTable"
          style="width: 100%"
          highlight-current-row
          @selection-change="handleSelectionChange"
          v-loading="tableLoading"
        >
          <!-- 财产物品名 -->
          <!-- <el-table-column prop="name" label="入住人"> </el-table-column> -->
          <!-- 可多选 -->
          <el-table-column type="selection"> </el-table-column>

          <!-- 信息 -->
          <!-- <el-table-column prop="message" label="入住人电话"> </el-table-column> -->

          <!-- 投诉时间 -->
          <el-table-column prop="buildingId" label="楼栋"> </el-table-column>

          <!-- 回应时间 -->
          <el-table-column prop="roomId" label="房间号"> </el-table-column>
        </el-table>
        <Pagination
          :total="count"
          :page="PageNo"
          @pagination="handlePagination"
          :hidden="roomTable.length === 0"
        />
      </div>
    </div>
  </el-dialog>
</template>

<style lang="scss" scoped></style>
