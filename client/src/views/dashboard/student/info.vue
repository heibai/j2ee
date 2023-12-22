<template>
  <div id="student-info">
    <h1 class="main-title">宿舍概览</h1>
    <div class="wrapper">
      <PanelGroup></PanelGroup>
    </div>
    <h1 class="main-title">当前入住</h1>
    <div class="wrapper">
      <StudentList :studentsData="studentsData"></StudentList>
    </div>
    <h1 class="main-title">宿舍评价</h1>
    <div class="wrapper">
      <Evaluates :evaluatesData="evaluatesData"></Evaluates>
    </div>

    <!-- 小区公共财产查看 -->
    <h1 class="main-title">小区公共财产</h1>
    <div class="wrapper">
      <PanelGroup></PanelGroup>
    </div>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import StudentList from './components/StudentList'
import Evaluates from './components/Evaluates'
export default {
  name: 'Info',
  components: {
    PanelGroup,
    StudentList,
    Evaluates
  },
  data() {
    return {
      studentsData: [],
      evaluatesData: []
    }
  },
  computed: {
    name() {
      return this.$store.getters.name
    }
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.wrapper {
  margin: 40px 0;
}
</style>
