package com.jsoo.water.service;

import java.util.List;
import com.jsoo.water.domain.Water;

/**
 * 桶装水信息Service接口
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
public interface IWaterService
{
    /**
     * 查询桶装水信息
     * 
     * @param waterId 桶装水信息主键
     * @return 桶装水信息
     */
    public Water selectWaterByWaterId(Long waterId);

    /**
     * 查询桶装水信息列表
     * 
     * @param water 桶装水信息
     * @return 桶装水信息集合
     */
    public List<Water> selectWaterList(Water water);

    /**
     * 新增桶装水信息
     * 
     * @param water 桶装水信息
     * @return 结果
     */
    public int insertWater(Water water);

    /**
     * 修改桶装水信息
     * 
     * @param water 桶装水信息
     * @return 结果
     */
    public int updateWater(Water water);

    /**
     * 批量删除桶装水信息
     * 
     * @param waterIds 需要删除的桶装水信息主键集合
     * @return 结果
     */
    public int deleteWaterByWaterIds(Long[] waterIds);

    /**
     * 删除桶装水信息信息
     * 
     * @param waterId 桶装水信息主键
     * @return 结果
     */
    public int deleteWaterByWaterId(Long waterId);
}
