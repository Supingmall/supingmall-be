package com.github.shoppingmallproject.web.dto.Order;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String ship;
    private String orderRequest;
}
