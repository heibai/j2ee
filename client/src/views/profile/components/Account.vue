<template>
  <el-form label-position="top">
    <el-form-item label="用户名">
      <el-input v-model.trim="name" />
    </el-form-item>
    <!-- 身份 -->
    <el-form-item label="身份">
      <el-input :value="roleString" disabled />
    </el-form-item>
    <el-form-item label="新密码">
      <el-input v-model.trim="password" />
    </el-form-item>
    <el-form-item>
      <div class="btn-wrapper">
        <el-button type="primary" @click="submit">更新</el-button>
      </div>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserInfo } from '@/api/user'
import MajorSelector from '@/components/MajorSelector'
import { mapGetters } from 'vuex'
import { transRoleString } from '@/filters'
export default {
  components: {
    MajorSelector
  },
  computed: {
    ...mapGetters(['role']),
    roleString() {
      return transRoleString(this.role)
    }
  },
  data() {
    return {
      name: '',
      sex: 0,
      phone: '',
      facultyWithMajor: null,
      password: ''
    }
  },
  methods: {
    submit() {
      // 密码需要大于6位
      if (this.password.length < 6) {
        this.$message({
          type: 'error',
          message: '密码长度不能小于6位!'
        })
        return
      }
      updateUserInfo({
        name: this.name,
        password: this.password,
        id: this.$store.getters.userInfo.id
      }).then(() => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        this.$store.dispatch('user/getInfo').then(() => {
          location.reload()
        })
      })
    }
  },
  mounted() {
    this.name = this.$store.getters.userInfo.name
  }
}
</script>

<style lang="scss" scoped>
.btn-wrapper {
  display: flex;
  .tips {
    margin-left: 20px;
    color: rgba($color: #000000, $alpha: 0.5);
  }
}
</style>
