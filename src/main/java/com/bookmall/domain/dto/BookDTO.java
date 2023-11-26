package com.bookmall.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    private Long id;
    private String name;
    private String imgs;
    private BigDecimal price;

}
