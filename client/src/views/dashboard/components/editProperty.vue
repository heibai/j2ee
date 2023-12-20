<script>
import propertyForm from './propertyForm.vue'
export default {
  name: 'editProperty',
  components: {
    propertyForm
  },
  data() {
    return {
      fromData: {}
    }
  },
  methods: {
    handleSubmit(data) {
      //  处理修改逻辑
    },
    show() {
      this.visible = true
    }
  }
}
</script>
<template>
  <propertyForm title="编辑物品" @finish="handleSubmit" :submitText="新增">
  </propertyForm>
</template>
<style scoped lang="scss"></style>
