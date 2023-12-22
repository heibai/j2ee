import { login, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'

const state = {
  userInfo: JSON.parse(getToken()) || {},
  token: getToken(), // 由 cookie 获取 token
  name: '',
  avatar: '',
  introduction: '',
  role: JSON.parse(getToken()).role,
  room: undefined,
  floor: undefined,
  building: undefined,
  newUser: false,
  manageBuildings: []
}

const mutations = {
  SET_USERINFO: (state, info) => {
    state.userInfo = info
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, role) => {
    state.role = role
  },
  SET_ROOM: (state, room) => {
    state.room = room
  },
  SET_FLOOR: (state, floor) => {
    state.floor = floor
  },
  SET_BUILDING: (state, building) => {
    state.building = building
  },
  SET_NEWUSER: (state, value) => {
    state.newUser = value
  },
  SET_MANAGEBUILDINGS: (state, value) => {
    state.manageBuildings = value
  }
}

const actions = {
  // user login 在登录时调用，获取用户 Token 并写入 Store 和 Localstroge
  login({ commit, dispatch }, userInfo) {
    const { account, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ userId: account.trim(), password: password, role: 'superAdmin' })
        .then(response => {
          const { data } = response
          // data为用户信息
          commit('SET_TOKEN', data)
          commit('SET_USERINFO', data)
          commit('SET_ROLES', data.role)
          setToken(JSON.stringify(data))

          dispatch('permission/generateRoutes', data.role, { root: true })
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout({ commit, dispatch }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', '')
      removeToken()
      resetRouter()
      // 复位视图，让用户处于未登录状态
      dispatch('tagsView/delAllViews', null, { root: true })
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  changeRoles({ commit, dispatch }, role) {
    return new Promise(async resolve => {
      const token = role + '-token'

      commit('SET_TOKEN', token)
      setToken(token)

      const { roles } = await dispatch('getInfo')

      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch('permission/generateRoutes', roles, {
        root: true
      })

      // dynamically add accessible routes
      router.addRoutes(accessRoutes)

      // reset visited views and cached views
      dispatch('tagsView/delAllViews', null, { root: true })

      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
