<script>
import propertyForm from './propertyForm.vue'
import { addProperty } from '@/api/property'
export default {
  name: 'addProperty',
  components: {
    propertyForm
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    handleSubmit(data) {
      //  处理新增逻辑
      addProperty(data)
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
  <propertyForm
    @finish="handleSubmit"
    submitText="新增"
    :visible.sync="visible"
  >
  </propertyForm>
</template>
<style scoped lang="scss"></style>
