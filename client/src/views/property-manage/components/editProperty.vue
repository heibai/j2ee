<script>
import propertyForm from './propertyForm.vue'
import { updateProperty } from '@/api/property'
export default {
  name: 'editProperty',
  components: {
    propertyForm
  },
  data() {
    return {
      visible: false,
      formData: {}
    }
  },
  methods: {
    handleSubmit(data) {
      //  处理修改逻辑
      data.propertyId = data.id
      updateProperty(data)
        .then(() => {
          this.$message.success('修改成功')
          this.visible = false
          this.$emit('operateFinish')
        })
        .finally(() => {
          this.updateLoading = false
        })
    },
    show(row) {
      this.$refs.form.initFormData(row)
      this.visible = true
    }
  }
}
</script>
<template>
  <propertyForm
    @finish="handleSubmit"
    :visible.sync="visible"
    ref="form"
    submitText="修改"
    :editMode="true"
  >
  </propertyForm>
</template>
<style scoped lang="scss"></style>
