package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookmall.domain.entity.Order;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Update("update t_order set state = '已支付' where order_no = #{orderNo}")
    void payOrder(String orderNo);

    @MapKey("id")
    List<Map<String, Object>> selectByUserId(int userId);

    @Update("update t_order set state = '已收货' where order_no = #{orderNo}")
    boolean receiveOrder(String orderNo);

    Map<String, Object> selectByOrderNo(String orderNo);
}
