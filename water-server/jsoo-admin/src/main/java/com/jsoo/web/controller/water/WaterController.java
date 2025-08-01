package com.jsoo.web.controller.water;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jsoo.common.annotation.Log;
import com.jsoo.common.core.controller.BaseController;
import com.jsoo.common.core.domain.AjaxResult;
import com.jsoo.common.enums.BusinessType;
import com.jsoo.water.domain.Water;
import com.jsoo.water.service.IWaterService;
import com.jsoo.common.utils.poi.ExcelUtil;
import com.jsoo.common.core.page.TableDataInfo;

/**
 * 桶装水信息Controller
 * 
 * @author 辛浩杰
 * @date 2024-10-30
 */
@RestController
@RequestMapping("/water/water")
public class WaterController extends BaseController
{
    @Autowired
    private IWaterService waterService;

    /**
     * 查询桶装水信息列表
     */
    @PreAuthorize("@ss.hasPermi('water:water:list')")
    @GetMapping("/list")
    public TableDataInfo list(Water water)
    {
        startPage();
        List<Water> list = waterService.selectWaterList(water);
        return getDataTable(list);
    }

    /**
     * 导出桶装水信息列表
     */
    @PreAuthorize("@ss.hasPermi('water:water:export')")
    @Log(title = "桶装水信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Water water)
    {
        List<Water> list = waterService.selectWaterList(water);
        ExcelUtil<Water> util = new ExcelUtil<Water>(Water.class);
        util.exportExcel(response, list, "桶装水信息数据");
    }

    /**
     * 获取桶装水信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('water:water:query')")
    @GetMapping(value = "/{waterId}")
    public AjaxResult getInfo(@PathVariable("waterId") Long waterId)
    {
        return success(waterService.selectWaterByWaterId(waterId));
    }

    /**
     * 新增桶装水信息
     */
    @PreAuthorize("@ss.hasPermi('water:water:add')")
    @Log(title = "桶装水信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Water water)
    {
        //判断水票价格不能大于桶装水的价格
        if (water.getCouponPrice().compareTo(water.getWaterPrice())>0){
            return error("水票售价不能大于桶装水售价");
        }
        return toAjax(waterService.insertWater(water));
    }

    /**
     * 修改桶装水信息
     */
    @PreAuthorize("@ss.hasPermi('water:water:edit')")
    @Log(title = "桶装水信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Water water)
    {
        //判断水票价格不能大于桶装水的价格
        if (water.getCouponPrice().compareTo(water.getWaterPrice())>0){
            return error("水票售价不能大于桶装水售价");
        }
        return toAjax(waterService.updateWater(water));
    }

    /**
     * 删除桶装水信息
     */
    @PreAuthorize("@ss.hasPermi('water:water:remove')")
    @Log(title = "桶装水信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{waterIds}")
    public AjaxResult remove(@PathVariable Long[] waterIds)
    {
        return toAjax(waterService.deleteWaterByWaterIds(waterIds));
    }
}
