package com.github.shoppingmallproject.web.dto.cart;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {
    private Integer cartId;
    private Integer productOptionId;
    private Integer userId;
    private Integer cartAmount;

    private Integer totalAmount;
    private Integer addAmount;
}
