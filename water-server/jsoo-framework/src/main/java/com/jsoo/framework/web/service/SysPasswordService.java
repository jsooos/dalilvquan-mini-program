package com.jsoo.framework.web.service;

import com.jsoo.common.constant.CacheConstants;
import com.jsoo.common.core.domain.entity.SysUser;
import com.jsoo.common.exception.user.UserPasswordNotMatchException;
import com.jsoo.common.exception.user.UserPasswordRetryLimitExceedException;
import com.jsoo.common.utils.CacheUtils;
import com.jsoo.common.utils.SecurityUtils;
import com.jsoo.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * 登录密码方法
 * 
 * @author jsoo
 */
@Component
public class SysPasswordService
{
    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @return 缓存Cache
     */
    private Cache getCache()
    {
        return CacheUtils.getCache(CacheConstants.PWD_ERR_CNT_KEY);
    }

    public void validate(SysUser user)
    {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();
        Integer retryCount = getCache().get(username, Integer.class);
        if (retryCount == null)
        {
            retryCount = 0;
        }
        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }
        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            getCache().put(username, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        getCache().evictIfPresent(loginName);
    }
}
