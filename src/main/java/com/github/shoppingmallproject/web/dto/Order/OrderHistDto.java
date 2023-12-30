package com.github.shoppingmallproject.web.dto.Order;

import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.orderItem.OrderItemEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    private Integer orderId;
    private String orderStatus;
    private String orderAt;
    private String ship;
    private String orderRequest;
    private Integer orderTotalPrice;
    private List<OrderItemDto> orderItems;

    // 기본 생성자 추가
    public OrderHistDto() {
        this.orderId = null;
        this.orderStatus = null;
        this.orderAt = null;
        this.ship = null;
        this.orderRequest = null;
        this.orderTotalPrice = null;
        this.orderItems = new ArrayList<>();
    }

    // 생성자에서 orderItems 초기화
    public OrderHistDto(OrderEntity order) {
        this.orderId = order.getOrderId();
        this.orderStatus = order.getOrderStatus();
        this.orderAt = order.getOrderAt().toString();
        this.ship = order.getShip();
        this.orderRequest = order.getOrderRequest();
        this.orderTotalPrice = order.getOrderTotalPrice();
        this.orderItems = new ArrayList<>();  // orderItems 초기화

        // OrderItemDto 리스트 설정
        for (OrderItemEntity orderItem : order.getOrderItemEntities()) {
            OrderItemDto orderItemDto = OrderItemDto.createOrderItemDto(orderItem);
            this.orderItems.add(orderItemDto);
        }
    }

    // 정적 팩토리 메서드
    public static OrderHistDto createOrderHistDto(OrderEntity order) {
        OrderHistDto orderHistDto = new OrderHistDto();
        orderHistDto.setOrderId(order.getOrderId());
        orderHistDto.setOrderAt(String.valueOf(order.getOrderAt()));
        orderHistDto.setOrderStatus(order.getOrderStatus());
        orderHistDto.setShip(order.getShip());
        orderHistDto.setOrderRequest(order.getOrderRequest());
        orderHistDto.setOrderTotalPrice(order.getOrderTotalPrice());

        // OrderItemDto 리스트 설정
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItemEntity orderItem : order.getOrderItemEntities()) {
            OrderItemDto orderItemDto = OrderItemDto.createOrderItemDto(orderItem);
            orderItemDtos.add(orderItemDto);
        }
        orderHistDto.setOrderItems(orderItemDtos);

        return orderHistDto;
    }

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        this.orderItems.add(orderItemDto);
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderAt(String orderAt) {
        this.orderAt = orderAt;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public void setOrderRequest(String orderRequest) {
        this.orderRequest = orderRequest;
    }

    public void setOrderTotalPrice(Integer orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}
