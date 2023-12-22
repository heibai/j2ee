<script>
import dialogForm from './dialogForm.vue'
import { createResidentFees } from '@/api/fees'
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
      console.log('req', data)
      //  处理新增逻辑
      createResidentFees(data)
        .then(() => {
          this.$message.success('发布成功')
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
