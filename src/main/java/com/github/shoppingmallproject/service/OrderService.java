package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.order.OrderJpa;
import com.github.shoppingmallproject.repository.orderItem.OrderItemEntity;
import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoJpa;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.web.dto.Order.OrderDto;
import com.github.shoppingmallproject.web.dto.Order.OrderHistDto;
import com.github.shoppingmallproject.web.dto.Order.OrderItemDto;
import com.github.shoppingmallproject.web.dto.Order.UserDto;
import javax.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpa orderJpa;
    private final UserJpa userJpa;
    private final ProductJpa productJpa;
    private final ProductPhotoJpa productPhotoJpa;

    // 주문을 위한 로직
    public Integer order(OrderDto orderDto, String name) {
        UserDto userDto = orderDto.getUser();
        List<OrderItemDto> orderItemsDto = orderDto.getOrders();

        UserEntity user = userJpa.findById(userDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemsDto) {
            ProductEntity product = productJpa.findById(orderItemDto.getProductId())
                    .orElseThrow(EntityNotFoundException::new);

            ProductOptionEntity selectedOption = new ProductOptionEntity();
            OrderItemEntity orderItem = OrderItemEntity.createOrderItem(product, selectedOption, orderItemDto.getStock());
            orderItems.add(orderItem);
        }

        OrderEntity order = OrderEntity.createOrder(user, orderItems);
        order.setOrderRequest(userDto.getOrderRequest());
        order.setOrderStatus("접수");
        order.setOrderTotalPrice(calculateOrderTotalPrice(orderItems));
        orderJpa.save(order);

        return order.getOrderId();
    }

    // 주문 아이템들의 가격을 합산하여 주문 총 가격 계산
    public Integer calculateOrderTotalPrice(List<OrderItemEntity> orderItems) {

        return orderItems.stream()
                .mapToInt(OrderItemEntity::getOrderPrice)
                .sum();
    }

    // 주문 목록을 조회하기 위한 로직
    @Transactional
    public List<OrderHistDto> getOrderList(String name) {
        UserEntity user = userJpa.findByEmail(name);
        List<OrderEntity> orders = orderJpa.findAllByUserEntityJoin(user);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (OrderEntity order : orders) {
            OrderHistDto orderHistDto = OrderHistDto.createOrderHistDto(order);
            List<OrderItemEntity> orderItems = order.getOrderItemEntities();

            for (OrderItemEntity orderItem : orderItems) {
                ProductEntity productEntity = orderItem.getProductOptionEntity().getProductEntity();
                ProductPhotoEntity productPhoto = getProductPhoto(productEntity);


                if (productPhoto != null) {
                    OrderItemDto orderItemDto = OrderItemDto.createOrderItemDto(orderItem);
                    orderHistDto.addOrderItemDto(orderItemDto);
                }
            }

            orderHistDtos.add(orderHistDto);
        }

        return orderHistDtos;
    }

    private ProductPhotoEntity getProductPhoto(ProductEntity productEntity) {

        return productPhotoJpa.findByProductEntity(productEntity);
    }

    // DB에 있는 email과 주문자 email 대조함
    @Transactional
    public boolean validateOrder(Integer orderId, String email) {
        UserEntity curUser = userJpa.findByEmail(email);
        OrderEntity order = orderJpa.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        UserEntity savedUser = order.getUserEntity();

        return StringUtils.equals(curUser.getEmail(), savedUser.getEmail());
    }

//    // 주문 취소하는 로직 구현 service
//    public void cancelOrder(Integer orderId) {
//        OrderEntity order = orderJpa.findById(orderId)
//                .orElseThrow(EntityNotFoundException::new);
//        order.cancelOrder();
//    }

    // 장바구니에서 주문할 상품 데이터를 전달받아서 주문 생성
    public Integer orders(List<OrderDto> orderDtoList, String email) {
        UserEntity user = userJpa.findByEmail(email);
        List<OrderItemEntity> orderItemList = new ArrayList<>();

        // 주문할 상품 리스트
        for (OrderDto orderDto : orderDtoList) {
            List<OrderItemDto> orderItemsDto = orderDto.getOrders();

            for (OrderItemDto orderItemDto : orderItemsDto) {
                ProductEntity product = productJpa.findById(orderItemDto.getId())
                        .orElseThrow(EntityNotFoundException::new);

                ProductOptionEntity selectedOption = new ProductOptionEntity();
                OrderItemEntity orderItem = OrderItemEntity.createOrderItem(product, selectedOption,orderItemDto.getStock());
                orderItemList.add(orderItem);
            }
        }

        OrderEntity order = OrderEntity.createOrder(user, orderItemList);
        orderJpa.save(order);

        if (order.getOrderRequest() == null) {
            System.out.println("DEBUG: orderRequest is null");
        }

        return order.getOrderId();
    }
}
