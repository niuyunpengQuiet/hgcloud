package com.hgcloud.sys.modular.sys;

import org.springframework.stereotype.Service;
import com.hgcloud.sys.api.SysApi;
import com.hgcloud.sys.core.util.SysPasswordUtil;

/**
 * 系统模块综合API接口实现类
 *
 * @author xuyuxiang
 * @date 2022/9/26 14:30
 **/
@Service
public class SysApiProvider implements SysApi {

    @Override
    public String getDefaultPassword() {
        return SysPasswordUtil.getDefaultPassword();
    }
}
