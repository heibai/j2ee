import Vue from 'vue'
import store from '@/store'

const checkRole = function(roleString) {
  let roleList = roleString.split(',')
  let role = store.getters && store.getters.role // 获取到用户角色
  return roleList.includes(role)
}

const hasPermission = Vue.directive('has', {
  inserted: function(el, binding, vnode) {
    let permissionRole = binding.value // 获取到 v-hasPerm="'admin'" 中 admin
    if (!checkRole(permissionRole)) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  },
  update: function(el, binding, vnode) {
    let permissionRole = binding.value // 获取到 v-hasPerm="'admin'" 中 admin
    if (!checkRole(permissionRole)) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  },
  bind: function(el, binding, vnode) {
    let permissionRole = binding.value // 获取到 v-hasPerm="'admin'" 中 admin
    if (!checkRole(permissionRole)) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  }
})

export default hasPermission
