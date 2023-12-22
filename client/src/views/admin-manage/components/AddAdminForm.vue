<template>
  <div class="AddAdminForm">
    <el-form class="form" ref="form" :model="formData" :rules="rules">
      <el-form-item label="姓号" required prop="name">
        <el-input v-model.trim="formData.name" placeholder="请输入"></el-input>
      </el-form-item>

      <el-form-item label="账户号" required prop="account">
        <el-input
          v-model.trim="formData.account"
          placeholder="请输入"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" required prop="password">
        <el-input
          v-model.trim="formData.password"
          placeholder="请输入"
        ></el-input>
      </el-form-item>
      <el-form-item label="选择身份" required>
        <el-radio v-model="formData.role" label="superAdmin">管理员</el-radio>
        <el-radio v-model="formData.role" label="worker">物业人员</el-radio>
      </el-form-item>
    </el-form>
    <div class="btn-wrapper">
      <el-popconfirm title="您确定填写的信息无误吗" @onConfirm="handleSubmit">
        <el-button
          type="primary"
          circle
          icon="el-icon-plus"
          slot="reference"
        ></el-button>
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
import { register } from '@/api/user'
export default {
  name: 'AddAdminForm',
  data() {
    const validateAccount = (rule, value, callback) => {
      // 只有为物业人员时才需要验证手机号
      if (this.formData.role !== 'worker') {
        callback()
        return
      }

      if (!/^1[3456789]\d{9}$/.test(value)) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码需要6位以上'))
      } else {
        callback()
      }
    }
    return {
      formData: {
        name: '',
        account: '',
        phone: '',
        password: '',
        role: 'superAdmin'
      },
      rules: {
        account: [
          {
            trigger: 'blur',
            validator: validateAccount
          }
        ],
        password: [
          {
            trigger: 'blur',
            validator: validatePassword
          }
        ]
      }
    }
  },
  methods: {
    handleSubmit() {
      // 验证是否有权限
      if (this.$store.state.user.role !== 'superAdmin') {
        this.$message.error('您没有权限进行此操作')
        return
      }
      this.$refs.form.validate(result => {
        if (result) {
          this.formData.userId = this.formData.account
          register(this.formData).then(() => {
            this.$message.success('注册成功')
            this.$parent.fetchAdminTableData().then(() => {
              this.$message.success('数据已更新')
            })
          })
        } else {
          this.$message.error('请填充完整信息')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.AddAdminForm {
  position: relative;
  .btn-wrapper {
    position: absolute;
    right: 0px;
    bottom: -70px;
    right: 10px;
    .el-button {
      width: 50px;
      height: 50px;
    }
  }
}
</style>
