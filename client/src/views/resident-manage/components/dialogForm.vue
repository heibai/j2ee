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
          name: '',
          userId: ''
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
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          let checkInUser = this.$emit('finish', this.formData)
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
