package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.OrderService;
import com.github.shoppingmallproject.web.dto.Order.OrderDto;
import com.github.shoppingmallproject.web.dto.Order.OrderHistDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 상품 구매 post (REST Api)
    @PostMapping(value = "/order")
    public ResponseEntity<?> order(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = customUserDetails.getUsername();
        Integer orderId;

        try {
            orderDto.getUser().setOrderRequest("조심히 부탁드립니다.");
            orderId = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    // 구매이력 조회 페이지
    @GetMapping("/orders")
    public String orderHist(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        List<OrderHistDto> ordersHistDtoList = orderService.getOrderList(customUserDetails.getUsername());
        model.addAttribute("orders", ordersHistDtoList);
        return "order/orderHist";
    }

//    // 주문 취소를 위한 컨트롤러
//    @PostMapping("/order/{orderId}/cancel")
//    public ResponseEntity<?> cancelOrder(
//            @PathVariable("orderId") Integer orderId,
//            @AuthenticationPrincipal CustomUserDetails customUserDetails
//    ) {
//        if (!orderService.validateOrder(orderId, customUserDetails.getUsername())) {
//            return new ResponseEntity<>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
//        }
//
//        orderService.cancelOrder(orderId);
//        return new ResponseEntity<>(orderId, HttpStatus.OK);
//    }
}
