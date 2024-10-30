package com.jsoo.water.service;

import java.util.List;
import com.jsoo.water.domain.BarrelledWater;

/**
 * 桶装水信息Service接口
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
public interface IBarrelledWaterService 
{
    /**
     * 查询桶装水信息
     * 
     * @param waterId 桶装水信息主键
     * @return 桶装水信息
     */
    public BarrelledWater selectBarrelledWaterByWaterId(Long waterId);

    /**
     * 查询桶装水信息列表
     * 
     * @param barrelledWater 桶装水信息
     * @return 桶装水信息集合
     */
    public List<BarrelledWater> selectBarrelledWaterList(BarrelledWater barrelledWater);

    /**
     * 新增桶装水信息
     * 
     * @param barrelledWater 桶装水信息
     * @return 结果
     */
    public int insertBarrelledWater(BarrelledWater barrelledWater);

    /**
     * 修改桶装水信息
     * 
     * @param barrelledWater 桶装水信息
     * @return 结果
     */
    public int updateBarrelledWater(BarrelledWater barrelledWater);

    /**
     * 批量删除桶装水信息
     * 
     * @param waterIds 需要删除的桶装水信息主键集合
     * @return 结果
     */
    public int deleteBarrelledWaterByWaterIds(Long[] waterIds);

    /**
     * 删除桶装水信息信息
     * 
     * @param waterId 桶装水信息主键
     * @return 结果
     */
    public int deleteBarrelledWaterByWaterId(Long waterId);
}
