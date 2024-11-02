import request from '@/utils/request'

// 查询桶装水信息列表
export function listWater(query) {
  return request({
    url: '/water/water/list',
    method: 'get',
    params: query
  })
}

// 查询桶装水信息详细
export function getWater(waterId) {
  return request({
    url: '/water/water/' + waterId,
    method: 'get'
  })
}

// 新增桶装水信息
export function addWater(data) {
  return request({
    url: '/water/water',
    method: 'post',
    data: data
  })
}

// 修改桶装水信息
export function updateWater(data) {
  return request({
    url: '/water/water',
    method: 'put',
    data: data
  })
}

// 删除桶装水信息
export function delWater(waterId) {
  return request({
    url: '/water/water/' + waterId,
    method: 'delete'
  })
}
