package com.github.shoppingmallproject.web.dto.Order;

import com.github.shoppingmallproject.repository.orderItem.OrderItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private Integer id; // 예시로 추가한 필드, 필요에 따라 수정
    private String color;
    private String productSize;
    private Integer stock; // 예시로 추가한 필드, 필요에 따라 수정
    private Integer productId;

    // OrderItemEntity를 기반으로 OrderItemDto를 생성하는 메서드
    public static OrderItemDto createOrderItemDto(OrderItemEntity orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getOrderItemId()); // 필드명 수정
        orderItemDto.setColor(orderItem.getProductOptionEntity().getColor());
        orderItemDto.setProductSize(orderItem.getProductOptionEntity().getProductSize());
        orderItemDto.setStock(orderItem.getItemAmount());

        return orderItemDto;
    }

}
