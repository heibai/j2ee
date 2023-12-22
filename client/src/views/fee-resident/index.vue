<script>
import RecordTable from './components/RecordTable'
import Pagination from '@/components/Pagination'
import paginationMixins from '@/mixins/paginationMixins'
import { getFeesList } from '@/api/fees'
export default {
  name: 'PublicPropertyManage',
  components: {
    RecordTable,
    Pagination
  },
  mixins: [paginationMixins],
  data() {
    return {
      tableData: []
    }
  },
  provide() {
    return {
      operateFinish: this.operateFinish
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    handleAdd() {
      this.$refs.addForm.show()
    },
    operateFinish() {
      this.getTableData()
    },
    async handlePagination({ page, limit }) {
      this.PageNo = page
      this.PageSize = limit
      this.getTableData()
    },

    async getTableData() {
      const params = {
        PageNo: this.PageNo,
        PageSize: this.PageSize,
        roomId: this.$store.getters.room
      }
      if (!params.roomId) {
        setTimeout(async () => {
          params.roomId = this.$store.getters.room
          const { data } = await getFeesList(params)
          this.tableData = this.generationTable(data.records)
          this.count = this.tableData.length
        }, 1000)
      } else {
        const { data } = await getFeesList(params)
        this.tableData = this.generationTable(data.records)
        this.count = this.tableData.length
      }
    },

    generationTable(records) {
      console.log(records)
      let tableData = []
      records.forEach(record => {
        const roomInfo = record[0]
        const feeList = record[1]
        feeList.forEach(fee => {
          tableData.push({
            ...roomInfo,
            roomNum: roomInfo.roomId,
            ...fee,
            roomId: roomInfo.id
          })
        })
      })
      return tableData
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="wrapper"></div>
    <!-- 管理概览 -->
    <!-- 公共财产管理 -->
    <h1 class="main-title">
      <span>
        费用中心
      </span>
    </h1>

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
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding: 0px 60px 0px;
  .main-title {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .operation-container {
    margin: 20px 0;
  }

  .wrapper {
    border-radius: 5px;
    overflow: hidden;
    margin: 40px 0;
  }
}
</style>
