<template>
  <!--  :close-on-click-modal="false" -->

  <el-dialog
    class="visitor-create-modal"
    :title="submitText"
    :visible="visible"
    @update:visible="$emit('update:visible', $event)"
    width="600px"
    destroy-on-close
  >
    <el-form :model="formData" label-position="top" ref="form" :rules="rules">
      <el-form-item prop="name" label="入住人" required>
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item prop="userId" label="手机号" required>
        <!-- 使用textarea标签 -->
        <el-input v-model="formData.userId"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="$emit('update:visible', false)">
        取消
      </el-button>
      <el-button :loading="updateLoading" type="primary" @click="handleSubmit">
        {{ submitText }}
      </el-button>
    </span>
    <selectRoomDialog
      :visible.sync="selectRoomVisible"
      :checkInUser="checkInUser"
    ></selectRoomDialog>
  </el-dialog>
</template>

<script>
import selectRoomDialog from './selectRoomDialog.vue'
import { getUser } from '@/api/user'

export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    existFormData: {
      type: Object,
      default: () => {
        return {
          name: 'admin',
          userId: 'admin'
        }
      }
    },
    submitText: {
      type: String,
      default: '确定'
    },
    editMode: {
      type: Boolean,
      default: false
    }
  },
  components: {
    selectRoomDialog
  },
  data() {
    return {
      formData: this.existFormData,
      rules: {
        name: [{ required: true, message: '请输入物品名', trigger: 'blur' }],
        message: [{ required: true, message: '请输入描述', trigger: 'blur' }]
      },
      checkInUser: {},
      selectRoomVisible: false,
      updateLoading: false
    }
  },
  methods: {
    initFormData(row) {
      this.formData = row
    },
    async searchUser(data) {
      // 搜寻有没有对应的用户
      const res = await getUser(data)
      if (res.code !== 200) {
        this.$message({
          type: 'error',
          message: '没有找到对应的用户'
        })
      } else {
        console.log(res)
        return res.data
      }
    },
    async handleSubmit() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          let checkInUser = await this.searchUser(this.formData)
          if (checkInUser) {
            this.checkInUser = checkInUser
            this.selectRoomVisible = true
          }
        }
      })
    }
  },
  mounted() {}
}
</script>

<style lang="scss" scoped></style>
