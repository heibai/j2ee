<script>
import propertyForm from './propertyForm.vue'
export default {
  name: 'addProperty',
  components: {
    propertyForm
  },
  data() {
    return {
      fromData: {},
      visible: false
    }
  },
  methods: {
    handleSubmit(data) {
      //  处理新增逻辑
    },
    show() {
      this.visible = true
    }
  }
}
</script>
<template>
  <propertyForm
    title="新增物品"
    @finish="handleSubmit"
    submitText="新增"
    :visible.sync="visible"
  >
  </propertyForm>
</template>
<style scoped lang="scss"></style>
