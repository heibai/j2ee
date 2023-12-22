import request from '@/utils/request'

export function getRepairReportPage(params) {
  return request({
    url: '/repairReport/getRepairReportPage',
    method: 'get',
    params
  })
}

export function createRepairReport(data) {
  return request({
    url: '/repairReport/createRepairReport',
    method: 'post',
    data
  })
}

// 删除
export function deleteRepairReport(params) {
  return request({
    url: '/repairReport/deleteRepairReport',
    method: 'get',
    params
  })
}

export function updateRepairReport(data) {
  return request({
    url: '/repairReport/updateRepairReport',
    method: 'post',
    data
  })
}
