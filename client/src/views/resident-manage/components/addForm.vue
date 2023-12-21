<script>
import dialogForm from './dialogForm.vue'
export default {
  name: 'addProperty',
  components: {
    dialogForm
  },
  data() {
    return {
      visible: false,
      selectRoomVisible: false
    }
  },
  methods: {
    show() {
      this.visible = true
    },
    hidden() {
      this.visible = false
    }
  }
}
</script>
<template>
  <dialogForm submitText="选择房间" :visible.sync="visible"> </dialogForm>
</template>
<style scoped lang="scss"></style>
