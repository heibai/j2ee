<template>
  <div class="app-container">
    <div>
      <el-row :gutter="20">
        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="账户信息" name="account">
                <account />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import Account from './components/Account'

export default {
  name: 'Profile',
  components: { Account },
  data() {
    return {
      activeTab: 'account'
    }
  },
  computed: {},
  created() {},
  methods: {}
}
</script>
