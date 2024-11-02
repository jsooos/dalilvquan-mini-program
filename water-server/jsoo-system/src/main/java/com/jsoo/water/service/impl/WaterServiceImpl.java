package com.jsoo.water.service.impl;

import java.util.List;
import com.jsoo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jsoo.water.mapper.WaterMapper;
import com.jsoo.water.domain.Water;
import com.jsoo.water.service.IWaterService;

/**
 * 桶装水信息Service业务层处理
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
@Service
public class WaterServiceImpl implements IWaterService
{
    @Autowired
    private WaterMapper waterMapper;

    /**
     * 查询桶装水信息
     * 
     * @param waterId 桶装水信息主键
     * @return 桶装水信息
     */
    @Override
    public Water selectWaterByWaterId(Long waterId)
    {
        return waterMapper.selectWaterByWaterId(waterId);
    }

    /**
     * 查询桶装水信息列表
     * 
     * @param water 桶装水信息
     * @return 桶装水信息
     */
    @Override
    public List<Water> selectWaterList(Water water)
    {
        return waterMapper.selectWaterList(water);
    }

    /**
     * 新增桶装水信息
     * 
     * @param water 桶装水信息
     * @return 结果
     */
    @Override
    public int insertWater(Water water)
    {
        water.setCreateTime(DateUtils.getNowDate());
        water.setDelFlag("N");
        return waterMapper.insertWater(water);
    }

    /**
     * 修改桶装水信息
     * 
     * @param water 桶装水信息
     * @return 结果
     */
    @Override
    public int updateWater(Water water)
    {
        water.setUpdateTime(DateUtils.getNowDate());
        return waterMapper.updateWater(water);
    }

    /**
     * 批量删除桶装水信息
     * 
     * @param waterIds 需要删除的桶装水信息主键
     * @return 结果
     */
    @Override
    public int deleteWaterByWaterIds(Long[] waterIds)
    {
        return waterMapper.deleteWaterByWaterIds(waterIds);
    }

    /**
     * 删除桶装水信息信息
     * 
     * @param waterId 桶装水信息主键
     * @return 结果
     */
    @Override
    public int deleteWaterByWaterId(Long waterId)
    {
        return waterMapper.deleteWaterByWaterId(waterId);
    }
}
