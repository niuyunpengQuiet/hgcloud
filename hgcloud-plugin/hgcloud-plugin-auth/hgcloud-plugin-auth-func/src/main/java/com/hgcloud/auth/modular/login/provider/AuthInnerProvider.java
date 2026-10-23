package com.hgcloud.auth.modular.login.provider;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hgcloud.auth.api.AuthInnerApi;
import com.hgcloud.auth.core.enums.SaClientTypeEnum;
import com.hgcloud.auth.modular.login.param.AuthAccountPasswordLoginParam;
import com.hgcloud.auth.modular.login.service.AuthService;
import com.hgcloud.common.pojo.CommonResult;


/**
 * AuthInnerProvider
 *
 * @author dongxiayu
 * @date 2024/1/12 23:26
 */
@Service
public class AuthInnerProvider implements AuthInnerApi {

    @Resource
    private AuthService authService;

    /**
     * B端账号密码内部环境登录
     *
     * @param username
     * @param password
     * @author dongxiayu
     * @date 2021/10/15 13:12
     */
    @Override
    public CommonResult<String> doLogin(String username, String password) {
        AuthAccountPasswordLoginParam authAccountPasswordLoginParam = new AuthAccountPasswordLoginParam();
        authAccountPasswordLoginParam.setAccount(username);
        authAccountPasswordLoginParam.setPassword(password);
        return CommonResult.data(authService.doLogin(authAccountPasswordLoginParam, SaClientTypeEnum.B.getValue()));
    }

    /**
     * B端账号密码内部环境使用token登出
     *
     * @param token
     * @author dongxiayu
     * @date 2021/10/15 13:12
     */
    @Override
    public CommonResult<String> doLogout(String token) {
        if(StrUtil.isNotBlank(token)){
            StpUtil.logoutByTokenValue(token);
        }
        return CommonResult.ok();
    }

}
