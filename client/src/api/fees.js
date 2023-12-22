import request from '@/utils/request'

// 获取费用列表
export function getFeesList(params) {
  return request({
    url: '/fees/getFeesPage',
    method: 'get',
    params
  })
}

//  一键发布费用
export function createResidentFees(params) {
  return request({
    url: '/fees/createResidentFees',
    method: 'get',
    params
  })
}

//  发布费用
export function publishSingleFee(data) {
  return request({
    url: '/fees/createFees',
    method: 'post',
    data
  })
}
// 删除
export function deleteFees(params) {
  return request({
    url: '/fees/deleteFees',
    method: 'get',
    params
  })
}

export function updateFees(data) {
  return request({
    url: '/fees/updateFees',
    method: 'post',
    data
  })
}

// 查询房间欠费
export function getRoomFees(params) {
  return request({
    url: '/fees/getRoomFees',
    method: 'get',
    params
  })
}

export function payRoomFees(params) {
  return request({
    url: '/fees/payRoomFees',
    method: 'get',
    params
  })
}
