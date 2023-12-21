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
      <!-- <el-form-item label="访问宿舍楼" prop="buildingId" required>
        <el-select v-model="formData.buildingId" placeholder="请选择" clearable>
          <el-option
            v-for="item in buildingsData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item prop="name" label="物品名" required>
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item prop="message" label="描述" required>
        <!-- 使用textarea标签 -->
        <el-input type="textarea" v-model="formData.message"></el-input>
      </el-form-item>
      <!-- <el-form-item prop="idNumber" label="身份证号" required>
        <el-input v-model="formData.idNumber"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex" required>
        <el-radio-group v-model="formData.sex">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
        </el-radio-group>
      </el-form-item> -->
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
