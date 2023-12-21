import request from '@/utils/request'

export function getRoomPage(params) {
  return request({
    url: '/room/getRoomPage',
    method: 'get',
    params
  })
}

// 可入住房间
export function getRoomPageAvailable(params) {
  return request({
    url: '/room/getResidentableRoom',
    method: 'get',
    params
  })
}

export function addRoom(data) {
  return request({
    url: '/room/createRoom',
    method: 'post',
    data
  })
}

// delete
export function deleteRoom(params) {
  return request({
    url: '/room/deleteRoom',
    method: 'get',
    params
  })
}
