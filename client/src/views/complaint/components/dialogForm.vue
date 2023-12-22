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
      <!-- <el-form-item prop="name" label="投诉人" required>
        <el-input v-model="formData.name"></el-input>
      </el-form-item> -->
      <el-form-item prop="message" label="投诉" required>
        <!-- 使用textarea标签 -->
        <el-input
          type="textarea"
          v-model="formData.message"
          :disabled="isResponse"
        >
        </el-input>
      </el-form-item>

      <!-- 回应 -->
      <el-form-item prop="responses" label="回应" v-has="'superAdmin'">
        <!-- 使用textarea标签 -->
        <el-input
          type="textarea"
          v-model="formData.responses"
          :disabled="!isResponse"
        ></el-input>
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
  </el-dialog>
</template>

<script>
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
          message: ''
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
  data() {
    return {
      formData: this.existFormData,
      rules: {
        name: [{ required: true, message: '请输入物品名', trigger: 'blur' }],
        message: [{ required: true, message: '请输入描述', trigger: 'blur' }]
      },
      updateLoading: false
    }
  },
  computed: {
    isResponse() {
      return this.$store.getters.role === 'superAdmin'
    }
  },
  methods: {
    initFormData(row) {
      this.formData = row
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.updateLoading = true
          this.$emit('finish', this.formData)
        }
      })
    }
  },
  mounted() {}
}
</script>

<style lang="scss" scoped></style>
