import request from '@/utils/request'

// 获取投诉列表
export function getComplaintList(params) {
  return request({
    url: '/complaint/getComplaintPage',
    method: 'get',
    params
  })
}

// 删除投诉
export function deleteComplaint(params) {
  return request({
    url: '/complaint/deleteComplaint',
    method: 'get',
    params
  })
}

// 添加投诉
export function addComplaint(data) {
  return request({
    url: '/complaint/createComplaint',
    method: 'post',
    data
  })
}

// 修改投诉
export function updateComplaint(data) {
  return request({
    url: '/complaint/updateComplaint',
    method: 'post',
    data
  })
}
