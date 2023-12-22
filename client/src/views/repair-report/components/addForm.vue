<script>
import dialogForm from './dialogForm.vue'
import { createRepairReport } from '@/api/repair-report'
export default {
  name: 'addProperty',
  components: {
    dialogForm
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    handleSubmit(data) {
      //  处理新增逻辑
      data.status = 1
      data.reporterId = this.$store.getters.userInfo.id
      data.reportTime = this.$moment()
        .format('YYYY-MM-DD HH:mm:ss')
        .replace(' ', 'T')
      createRepairReport(data)
        .then(() => {
          this.$message.success('新增成功')
          this.visible = false
          this.$emit('operateFinish')
        })
        .finally(() => {
          this.updateLoading = false
        })
    },
    show() {
      this.visible = true
    }
  }
}
</script>
<template>
  <dialogForm @finish="handleSubmit" submitText="新增" :visible.sync="visible">
  </dialogForm>
</template>
<style scoped lang="scss"></style>
