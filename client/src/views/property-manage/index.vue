<script>
import RecordTable from '@/components/RecordTable'
import addProperty from './components/addProperty.vue'
import editProperty from './components/editProperty.vue'
export default {
  name: 'PublicPropertyManage',
  components: {
    RecordTable,
    addProperty,
    editProperty
  },

  data() {
    return {
      buildingId: 0,
      getupTableData: [],
      backTableData: [],
      cleanTableData: [],
      editTableData: []
    }
  },
  methods: {
    handleAdd() {
      console.log(this.$refs)
      this.$refs.addProperty.show()
    },
    handleEdit(row) {
      console.log(row)
      this.editTableData = row
      this.$refs.editProperty.show()
    }
  }
}
</script>

<template>
  <div class="container">
    <div class="wrapper"></div>
    <!-- 管理概览 -->
    <!-- 公共财产管理 -->
    <h1 class="main-title">小区公共财产</h1>

    <!-- 新增按钮 -->
    <div class="operation-container">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="small"
        @click="handleAdd"
      >
        新增公共财产
      </el-button>
    </div>

    <div class="wrapper">
      <RecordTable
        type="public"
        title="公共财产"
        icon="el-icon-suitcase"
        :table-data="[]"
        :show-pagination="false"
      >
      </RecordTable>
    </div>

    <addProperty ref="addProperty"></addProperty>
    <editProperty ref="editProperty"> </editProperty>
  </div>
</template>

<style lang="scss" scoped>
.container {
  padding: 0px 60px 0px;
  .main-title {
    display: flex;
    align-items: center;
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
