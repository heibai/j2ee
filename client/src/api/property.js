import request from '@/utils/request'

export function getProperty() {
  return request({
    url: '/property/getPropertyPage',
    method: 'get'
  })
}

export function addProperty(data) {
  return request({
    url: '/property/createProperty',
    method: 'post',
    data
  })
}

export function updateProperty(data) {
  return request({
    url: '/property/updateProperty',
    method: 'post',
    data
  })
}

export function deleteProperty(params) {
  return request({
    url: '/property/deleteProperty',
    method: 'get',
    params
  })
}
