import request from '@/utils/request'

// 查询桶装水信息列表
export function listBarrelledWater(query) {
  return request({
    url: '/water/barrelledWater/list',
    method: 'get',
    params: query
  })
}

// 查询桶装水信息详细
export function getBarrelledWater(waterId) {
  return request({
    url: '/water/barrelledWater/' + waterId,
    method: 'get'
  })
}

// 新增桶装水信息
export function addBarrelledWater(data) {
  return request({
    url: '/water/barrelledWater',
    method: 'post',
    data: data
  })
}

// 修改桶装水信息
export function updateBarrelledWater(data) {
  return request({
    url: '/water/barrelledWater',
    method: 'put',
    data: data
  })
}

// 删除桶装水信息
export function delBarrelledWater(waterId) {
  return request({
    url: '/water/barrelledWater/' + waterId,
    method: 'delete'
  })
}
