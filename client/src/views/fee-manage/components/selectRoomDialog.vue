<script>
import paginationMixins from '@/mixins/paginationMixins'
import Pagination from '@/components/Pagination'
import { publishSingleFee } from '@/api/fees'
import { getRoomPage } from '@/api/room'
export default {
  components: {
    Pagination
  },
  mixins: [paginationMixins],
  inject: ['operateFinish'],
  props: {
    visible: { type: Boolean, default: false },
    formData: {
      type: Object,
      default: () => {
        return {
          price: '',
          type: '',
          deadline: null
        }
      }
    }
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
      getRoomPage(reqData).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.roomTable = res.data.records
          this.count = res.data.total
        }
        this.tableLoading = false
      })
    },

    publish() {
      this.$confirm('此操作将发布收费, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          this.selectRows.forEach(row => {
            let reqData = {
              ...this.formData,
              deadline: this.formData.time.replace(' ', 'T'),
              time: null,
              roomId: row.roomId
            }
            console.log(reqData)
            publishSingleFee(reqData).then(res => {
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
                  message: '发布失败'
                })
              }
            })
          })
        })
        .catch(e => {
          console.log(e)
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
    }
  }
}
</script>
<template>
  <el-dialog
    width="30%"
    title="选择房间"
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
            <el-button type="primary" @click="getTableData">查询</el-button>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="publish">发布</el-button>
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
