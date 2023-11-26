package com.bookmall.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 书籍规格及对应库存
 * @author Rayce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookStandard {
    private int bookId;
    private String value;
    private double price;
    private int store;

}
