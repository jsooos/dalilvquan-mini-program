package com.jsoo.water.mapper;

import java.util.List;
import com.jsoo.water.domain.BarrelledWater;

/**
 * 桶装水信息Mapper接口
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
public interface BarrelledWaterMapper 
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
     * 删除桶装水信息
     * 
     * @param waterId 桶装水信息主键
     * @return 结果
     */
    public int deleteBarrelledWaterByWaterId(Long waterId);

    /**
     * 批量删除桶装水信息
     * 
     * @param waterIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBarrelledWaterByWaterIds(Long[] waterIds);
}
