<script>
import editForm from './editForm.vue'
export default {
  name: 'RecordTable',
  components: {
    editForm
  },
  data() {
    return {}
  },
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    tableLoading: {
      type: Boolean,
      default: false
    },
    title: {
      type: String
    },
    type: {
      type: String,
      required: true
    },
    icon: {
      type: String
    }
  },
  computed: {},
  methods: {
    handleEdit(index, row) {
      let formData = JSON.parse(JSON.stringify(row))
      this.$refs.editForm.show(formData)
    }
  }
}
</script>

<template>
  <div class="record-table">
    <div class="table-title" v-if="title">
      <i v-if="icon" class="icon" :class="icon"></i> {{ title }}
    </div>
    <div class="table-wrapper">
      <el-table :data="tableData" style="width: 100%" v-loading="tableLoading">
        <!-- 财产物品名 -->
        <el-table-column prop="name" label="投诉人"> </el-table-column>

        <!-- 信息 -->
        <el-table-column prop="message" label="投诉信息"> </el-table-column>

        <!-- 投诉时间 -->
        <el-table-column prop="time" label="投诉时间"> </el-table-column>

        <!-- 回应时间 -->
        <el-table-column prop="replyTime" label="回应时间"> </el-table-column>

        <!-- 操作区 -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="handleEdit(scope.$index, scope.row)"
              >回复
            </el-button>
            <el-button
              type="danger"
              size="mini"
              @click="handleDelete(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <editForm ref="editForm" @operateFinish="$emit('operateFinish')">
    </editForm>
  </div>
</template>

<style lang="scss" scoped>
.table-title {
  background-color: #fff;
  padding: 20px;
  padding-bottom: 0px;
  font-size: 18px;
  font-weight: bold;
  color: rgba($color: #000000, $alpha: 0.5);
  display: flex;
  align-items: center;
  .icon {
    font-size: 24px;
    margin-right: 10px;
  }
}
.table-wrapper {
  padding-left: 20px;
  background-color: #fff;
  .bdge-wrapp {
    display: flex;
    justify-content: center;
    .early-bdge {
      height: 10px;
      width: 10px;
      border-radius: 50%;
      background-color: $color-danger;
    }
    .early-bdge-green {
      background-color: $color-success;
    }
  }
}
a {
  color: $color-primary;
  &:hover {
    text-decoration: underline;
  }
}
</style>
