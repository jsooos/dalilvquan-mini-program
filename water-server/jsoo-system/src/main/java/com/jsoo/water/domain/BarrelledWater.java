package com.jsoo.water.domain;

import java.math.BigDecimal;

import com.jsoo.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jsoo.common.annotation.Excel;

/**
 * 桶装水信息对象 jsoo_barrelled_water
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
public class BarrelledWater extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long waterId;

    /** 名称 */
    @Excel(name = "名称")
    private String waterName;

    /** 类型 */
    @Excel(name = "类型")
    private String waterType;

    /** 容量 */
    @Excel(name = "容量")
    private String waterCapacity;

    /** 售价（元） */
    @Excel(name = "售价", readConverterExp = "元=")
    private BigDecimal waterPrice;

    /** 图片 */
    @Excel(name = "图片")
    private String waterImage;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String delFlag;

    public void setWaterId(Long waterId) 
    {
        this.waterId = waterId;
    }

    public Long getWaterId() 
    {
        return waterId;
    }
    public void setWaterName(String waterName) 
    {
        this.waterName = waterName;
    }

    public String getWaterName() 
    {
        return waterName;
    }
    public void setWaterType(String waterType) 
    {
        this.waterType = waterType;
    }

    public String getWaterType() 
    {
        return waterType;
    }
    public void setWaterCapacity(String waterCapacity) 
    {
        this.waterCapacity = waterCapacity;
    }

    public String getWaterCapacity() 
    {
        return waterCapacity;
    }
    public void setWaterPrice(BigDecimal waterPrice) 
    {
        this.waterPrice = waterPrice;
    }

    public BigDecimal getWaterPrice() 
    {
        return waterPrice;
    }
    public void setWaterImage(String waterImage) 
    {
        this.waterImage = waterImage;
    }

    public String getWaterImage() 
    {
        return waterImage;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("waterId", getWaterId())
            .append("waterName", getWaterName())
            .append("waterType", getWaterType())
            .append("waterCapacity", getWaterCapacity())
            .append("waterPrice", getWaterPrice())
            .append("waterImage", getWaterImage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
