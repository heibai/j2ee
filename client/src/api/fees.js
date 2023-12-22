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
