import request from '@/utils/request'

// 创建住户
export function createResident(data) {
  return request({
    url: '/resident/createResident',
    method: 'post',
    data
  })
}

// 获取住户列表
export function getResidentPage(params) {
  return request({
    url: '/resident/getResidentPage',
    method: 'get',
    params
  })
}

// 退户
export function deleteResident(params) {
  return request({
    url: '/resident/deleteResident',
    method: 'get',
    params
  })
}
