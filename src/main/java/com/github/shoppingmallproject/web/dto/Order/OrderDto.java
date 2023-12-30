package com.github.shoppingmallproject.web.dto.Order;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private UserDto user;
    private List<OrderItemDto> orders;
}
