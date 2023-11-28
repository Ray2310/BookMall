package com.bookmall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Address;
import com.bookmall.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址请求逻辑层实现
 * @author Rayce
 */
@Service
public class AddressService extends ServiceImpl<AddressMapper, Address> {


    public List<Address> findAllById(Long userId) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return list(wrapper);

    }
}
