package com.jsoo.web.controller.monitor;

import com.jsoo.common.annotation.Log;
import com.jsoo.common.constant.CacheConstants;
import com.jsoo.common.core.controller.BaseController;
import com.jsoo.common.core.domain.AjaxResult;
import com.jsoo.common.core.domain.model.LoginUser;
import com.jsoo.common.core.page.TableDataInfo;
import com.jsoo.common.enums.BusinessType;
import com.jsoo.common.utils.CacheUtils;
import com.jsoo.common.utils.StringUtils;
import com.jsoo.system.domain.SysUserOnline;
import com.jsoo.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 * 
 * @author jsoo
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController
{
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName)
    {
        Collection<String> keys = CacheUtils.getkeys(CacheConstants.LOGIN_TOKEN_KEY);
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys)
        {
            LoginUser user = CacheUtils.get(CacheConstants.LOGIN_TOKEN_KEY, key, LoginUser.class);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName))
            {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            }
            else if (StringUtils.isNotEmpty(ipaddr))
            {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            }
            else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser()))
            {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            }
            else
            {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId)
    {
        CacheUtils.removeIfPresent(CacheConstants.LOGIN_TOKEN_KEY, tokenId);
        return success();
    }
}
