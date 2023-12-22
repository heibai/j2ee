<script>
export default {
  name: 'RecordTable',
  components: {},
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
  computed: {
    earlyDesc() {
      switch (this.type) {
        case 'getup':
          return '是否早起'
        case 'back':
          return '是否早归'
        default:
          return '记录值'
      }
    }
  },
  methods: {
    gotoStudentInfo(userId) {
      this.$router.push({ name: 'userInfo', query: { userId } })
    },
    gotoRoomInfo(roomId) {
      this.$router.push({ name: 'roomInfo', query: { roomId } })
    },
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
  <div class="record-table">
    <div class="table-title" v-if="title">
      <i v-if="icon" class="icon" :class="icon"></i> {{ title }}
    </div>
    <div class="table-wrapper">
      <!-- 新增按钮 -->

      <el-table :data="tableData" style="width: 100%" v-loading="tableLoading">
        <!-- 财产物品名 -->
        <el-table-column prop="name" label="物品名"> </el-table-column>

        <!-- 信息 -->
        <el-table-column prop="message" label="信息"> </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.record-table {
  .add-btn-wrapper {
    margin: 10px 20px;
  }
}
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
