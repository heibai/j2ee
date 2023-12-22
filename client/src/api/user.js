import request from '@/utils/request'

export function register(data) {
  return request({
    url: '/users/createUser',
    method: 'post',
    data
  })
}

export function login(data) {
  return request({
    url: '/users/login',
    method: 'post',
    data
  })
}

export function getUser(params) {
  return request({
    url: '/users/getUser',
    method: 'get',
    params
  })
}

export function getUserList(params) {
  return request({
    url: '/users/getUsersPage',
    method: 'get',
    params
  })
}

export function updateUserInfo(data) {
  return request({
    url: '/users/updateUser',
    method: 'post',
    data
  })
}

export function deleteUser(params) {
  return request({
    url: '/users/deleteUser',
    method: 'get',
    params
  })
}
