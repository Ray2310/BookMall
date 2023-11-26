package com.bookmall.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
用来接收前台下订单时，传来的‘books'参数
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderItem {

    /*
    商品id
     */
    private long id;
    /*
    商品规格
     */
    private String standard;
    /*
    数量
     */
    private int num;
}
