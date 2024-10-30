package com.jsoo.water.service.impl;

import java.util.List;
import com.jsoo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jsoo.water.mapper.BarrelledWaterMapper;
import com.jsoo.water.domain.BarrelledWater;
import com.jsoo.water.service.IBarrelledWaterService;

/**
 * 桶装水信息Service业务层处理
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
@Service
public class BarrelledWaterServiceImpl implements IBarrelledWaterService 
{
    @Autowired
    private BarrelledWaterMapper barrelledWaterMapper;

    /**
     * 查询桶装水信息
     * 
     * @param waterId 桶装水信息主键
     * @return 桶装水信息
     */
    @Override
    public BarrelledWater selectBarrelledWaterByWaterId(Long waterId)
    {
        return barrelledWaterMapper.selectBarrelledWaterByWaterId(waterId);
    }

    /**
     * 查询桶装水信息列表
     * 
     * @param barrelledWater 桶装水信息
     * @return 桶装水信息
     */
    @Override
    public List<BarrelledWater> selectBarrelledWaterList(BarrelledWater barrelledWater)
    {
        return barrelledWaterMapper.selectBarrelledWaterList(barrelledWater);
    }

    /**
     * 新增桶装水信息
     * 
     * @param barrelledWater 桶装水信息
     * @return 结果
     */
    @Override
    public int insertBarrelledWater(BarrelledWater barrelledWater)
    {
        barrelledWater.setCreateTime(DateUtils.getNowDate());
        barrelledWater.setDelFlag("N");
        return barrelledWaterMapper.insertBarrelledWater(barrelledWater);
    }

    /**
     * 修改桶装水信息
     * 
     * @param barrelledWater 桶装水信息
     * @return 结果
     */
    @Override
    public int updateBarrelledWater(BarrelledWater barrelledWater)
    {
        barrelledWater.setUpdateTime(DateUtils.getNowDate());
        return barrelledWaterMapper.updateBarrelledWater(barrelledWater);
    }

    /**
     * 批量删除桶装水信息
     * 
     * @param waterIds 需要删除的桶装水信息主键
     * @return 结果
     */
    @Override
    public int deleteBarrelledWaterByWaterIds(Long[] waterIds)
    {
        return barrelledWaterMapper.deleteBarrelledWaterByWaterIds(waterIds);
    }

    /**
     * 删除桶装水信息信息
     * 
     * @param waterId 桶装水信息主键
     * @return 结果
     */
    @Override
    public int deleteBarrelledWaterByWaterId(Long waterId)
    {
        return barrelledWaterMapper.deleteBarrelledWaterByWaterId(waterId);
    }
}
