package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.cart.CartEntity;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.CartService;
import com.github.shoppingmallproject.web.dto.cart.Cart;
import com.github.shoppingmallproject.web.dto.cart.SaveCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/product-detail")
public class AddToCartController {
    private final CartService cartService;

    // 카트 담기
//    @PostMapping("/cart")
//    public String addToCartItem(@RequestBody AddToCartBody addToCartBody){
//        Integer cartId = cartService.saveCart(addToCartBody);
//        return "장바구니 번호 : " + cartId + "에 성공적으로 담았습니다.";
//    }


    // 카트 조회
    @GetMapping("/cart")
    public List<Cart> findAllCart(){
        List<Cart> carts = cartService.findAllCart();
        return carts;
    }

//    @PostMapping("/cart")
//    public AddToCartBody registerCart(@RequestBody AddToCartBody addToCartBody){
//        return cartService.saveCart()
//    }
    @PostMapping("/cart")
    public SaveCartResponse registerCart(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @RequestParam Integer productOptionId,
                                         @RequestParam Integer addAmount
                               ){
        return cartService.saveCart(customUserDetails, productOptionId, addAmount);
    }
//    @PostMapping("/cart")
//    public ResponseEntity<String> addToCart(@RequestBody AddToCartBody addToCartBody) {
//        CartEntity addToCartItem = cartService.addToCart(addToCartBody);
//
//        if (addToCartItem != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("장바구니에 성공적으로 담았습니다");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니에 못 담음");
//        }
//    }


}
