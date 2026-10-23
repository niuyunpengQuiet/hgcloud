/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Hgcloud采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Hgcloud源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Hgcloud商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package com.hgcloud.dev.modular.password.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hgcloud.common.enums.CommonSortOrderEnum;
import com.hgcloud.common.exception.CommonException;
import com.hgcloud.common.page.CommonPageRequest;
import com.hgcloud.dev.modular.password.entity.DevWeakPassword;
import com.hgcloud.dev.modular.password.mapper.DevWeakPasswordMapper;
import com.hgcloud.dev.modular.password.param.DevWeakPasswordAddParam;
import com.hgcloud.dev.modular.password.param.DevWeakPasswordEditParam;
import com.hgcloud.dev.modular.password.param.DevWeakPasswordIdParam;
import com.hgcloud.dev.modular.password.param.DevWeakPasswordPageParam;
import com.hgcloud.dev.modular.password.service.DevWeakPasswordService;

import java.util.List;

/**
 * 弱密码库Service接口实现类
 *
 * @author yubaoshan
 * @date  2024/12/21 01:25
 **/
@Service
public class DevWeakPasswordServiceImpl extends ServiceImpl<DevWeakPasswordMapper, DevWeakPassword> implements DevWeakPasswordService {

    @Override
    public Page<DevWeakPassword> page(DevWeakPasswordPageParam devWeakPasswordPageParam) {
        QueryWrapper<DevWeakPassword> queryWrapper = new QueryWrapper<DevWeakPassword>().checkSqlInjection();
        if(ObjectUtil.isNotEmpty(devWeakPasswordPageParam.getSearchKey())) {
            queryWrapper.lambda().like(DevWeakPassword::getPassword, devWeakPasswordPageParam.getSearchKey());
        }
        if(ObjectUtil.isAllNotEmpty(devWeakPasswordPageParam.getSortField(), devWeakPasswordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(devWeakPasswordPageParam.getSortOrder());
            queryWrapper.orderBy(true, devWeakPasswordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(devWeakPasswordPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DevWeakPassword::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DevWeakPasswordAddParam devWeakPasswordAddParam) {
        DevWeakPassword devWeakPassword = BeanUtil.toBean(devWeakPasswordAddParam, DevWeakPassword.class);
        boolean repeatPassword = this.count(new LambdaQueryWrapper<DevWeakPassword>().eq(DevWeakPassword::getPassword, devWeakPassword.getPassword())) > 0;
        if(repeatPassword) {
            throw new CommonException("存在重复的密码，值为：{}", devWeakPassword.getPassword());
        }
        this.save(devWeakPassword);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DevWeakPasswordEditParam devWeakPasswordEditParam) {
        DevWeakPassword devWeakPassword = this.queryEntity(devWeakPasswordEditParam.getId());
        BeanUtil.copyProperties(devWeakPasswordEditParam, devWeakPassword);
        boolean repeatPassword = this.count(new LambdaQueryWrapper<DevWeakPassword>().eq(DevWeakPassword::getPassword, devWeakPassword.getPassword())
                .ne(DevWeakPassword::getId, devWeakPassword.getId())) > 0;
        if(repeatPassword) {
            throw new CommonException("存在重复的密码，值为：{}", devWeakPassword.getPassword());
        }
        this.updateById(devWeakPassword);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DevWeakPasswordIdParam> devWeakPasswordIdParamList) {
        List<String> weakPasswordIdList = CollStreamUtil.toList(devWeakPasswordIdParamList, DevWeakPasswordIdParam::getId);
        // 执行删除
        this.removeByIds(weakPasswordIdList);
    }

    @Override
    public DevWeakPassword detail(DevWeakPasswordIdParam devWeakPasswordIdParam) {
        return this.queryEntity(devWeakPasswordIdParam.getId());
    }

    @Override
    public DevWeakPassword queryEntity(String id) {
        DevWeakPassword devWeakPassword = this.getById(id);
        if (ObjectUtil.isEmpty(devWeakPassword)) {
            throw new CommonException("弱密码数据不存在，id值为：{}", id);
        }
        return devWeakPassword;
    }
}
