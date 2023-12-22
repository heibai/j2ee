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
      <!-- 截止时间 -->
      <el-form-item prop="time" label="截止时间" required>
        <!-- 使用日期组件,限制只能从往后的时间选 -->
        <el-date-picker
          v-model="formData.time"
          type="date"
          placeholder="选择日期"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd HH:mm:ss"
        ></el-date-picker>
      </el-form-item>

      <!-- 选择类型  0表示房费，为1表示水电费  select-->
      <el-form-item prop="type" label="类型" required>
        <!-- 使用select标签 -->
        <el-select v-model="formData.type" placeholder="请选择">
          <el-option label="租金" value="1"></el-option>
          <el-option label="水电费" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="price" label="金额" required>
        <el-input v-model="formData.price"> </el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button @click="$emit('update:visible', false)">
        取消
      </el-button>

      <el-button :loading="updateLoading" type="primary" @click="selectRoom">
        为特定住屋发布
      </el-button>

      <el-button :loading="updateLoading" type="primary" @click="handleSubmit">
        一键发布
      </el-button>
    </span>
    <selectRoomDialog
      :visible.sync="selectRoomVisible"
      :formData="formData"
    ></selectRoomDialog>
  </el-dialog>
</template>

<script>
import selectRoomDialog from './selectRoomDialog.vue'
import { toTimeStamp } from '@/filters'
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
  components: {
    selectRoomDialog
  },
  data() {
    return {
      formData: this.existFormData,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      rules: {
        name: [{ required: true, message: '请输入物品名', trigger: 'blur' }],
        message: [{ required: true, message: '请输入描述', trigger: 'blur' }]
      },
      selectRoomVisible: false,
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
    selectRoom() {
      this.selectRoomVisible = true
    },
    handleSubmit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.updateLoading = true
          this.formData.deadline = this.formData.time
          this.formData.status = 1
          console.log(this.formData)
          this.$emit('finish', this.formData)
        }
      })
    }
  },
  mounted() {}
}
</script>

<style lang="scss" scoped></style>
