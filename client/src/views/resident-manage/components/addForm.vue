<script>
import dialogForm from './dialogForm.vue'
import { getUser } from '@/api/user'
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
    handleSubmit(data) {
      // 搜寻有没有对应的用户
      // 如果有，就去到选择房间的dialog
      getUser(data).then(res => {
        if (res.data.length === 0) {
          this.$message({
            type: 'error',
            message: '没有找到对应的用户'
          })
        } else {
          console.log(res)
          return res
        }
      })
    },
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
  <dialogForm
    @finish="handleSubmit"
    submitText="选择房间"
    :visible.sync="visible"
  >
  </dialogForm>
</template>
<style scoped lang="scss"></style>
