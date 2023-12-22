import { login, getUser } from '@/api/user'
import { getResident } from '@/api/resident'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import { Message } from 'element-ui'
const state = {
  userInfo: JSON.parse(getToken()) || {},
  token: getToken(), // 由 cookie 获取 token
  name: '',
  avatar: '',
  introduction: '',
  role: '',
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
      login({ userId: account.trim(), password: password })
        .then(response => {
          // 检测是否登录成功

          const { data, code } = response
          if (code !== 200) {
            reject('登录失败，请检查账号密码')
            Message({
              message: '登录失败，请检查账号密码',
              type: 'error',
              duration: 5 * 1000
            })
          }
          // data为用户信息
          commit('SET_TOKEN', data)
          commit('SET_USERINFO', data)
          commit('SET_ROLES', data.role)
          commit('SET_NAME', data.name)
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
      commit('SET_ROLES', '')
      removeToken()
      resolve()
    })
  },

  getUserInfo({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      getUser({ id: state.userInfo.id })
        .then(response => {
          const { data } = response
          if (!data) {
            reject('验证失败，请重新登录')
          }
          commit('SET_USERINFO', data)
          commit('SET_ROLES', data.role)
          if (data.role === 'resident') {
            dispatch('getResidentInfo')
          }
          commit('SET_NAME', data.name)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },
  getResidentInfo({ commit, state }) {
    getResident({ userId: state.userInfo.id }).then(response => {
      const { data } = response
      console.log(data)
      if (!data) {
        $message.error('获取用户信息失败')
      }
      commit('SET_ROOM', data.roomId)
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
