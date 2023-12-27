package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.cart.CartEntity;
import com.github.shoppingmallproject.repository.cart.CartEntityJpaRepository;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpaRepository;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotAcceptException;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.CartMapper;
import com.github.shoppingmallproject.web.dto.cart.Cart;
import com.github.shoppingmallproject.web.dto.cart.SaveCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartEntityJpaRepository cartEntityJpaRepository;
    private final UserJpa userJpa;
    private final ProductOptionJpaRepository productOptionJpaRepository;

    // 장바구니 조회
    public List<Cart> findAllCart() {
        List<CartEntity> cartEntities = cartEntityJpaRepository.findAll();
        if (cartEntities.isEmpty()) throw new NotFoundException("카트에 담긴 상품이 없습니다.");
        return cartEntities.stream().map(CartMapper.INSTANCE::cartEntityToCart).collect(Collectors.toList());
    }

    public SaveCartResponse saveCart(CustomUserDetails customUserDetails, Integer productOptionId, Integer addAmount) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());

        ProductOptionEntity productOptionEntity = productOptionJpaRepository.findById(productOptionId)
                .orElseThrow(() -> new NotFoundException("해당되는 상품이 없습니다."));

        if (productOptionEntity.getStock() < addAmount)
            throw new NotFoundException("재고가 없습니다.");

        Integer total_cart_amount = 0;

        CartEntity savedCartEntity = cartEntityJpaRepository.findByUserEntityAndProductOptionEntity(userEntity, productOptionEntity);

        if (savedCartEntity == null) {
            CartEntity cartEntity = CartEntity.builder()
                    .productOptionEntity(productOptionEntity)
                    .cartAmount(addAmount)
                    .userEntity(userEntity)
                    .build();

            cartEntityJpaRepository.save(cartEntity);
            total_cart_amount = cartEntity.getCartAmount();
        } else {
            Integer cartAmountAndProductAmount = savedCartEntity.getCartAmount() + addAmount;
            if (cartAmountAndProductAmount > productOptionEntity.getStock())
                throw new NotFoundException("재고가 없습니다.");

            CartEntity cartEntity = CartEntity.builder()
                    .cartId(savedCartEntity.getCartId())
                    .productOptionEntity(productOptionEntity)
                    .userEntity(userEntity)
                    .cartAmount(cartAmountAndProductAmount)
                    .build();
            cartEntityJpaRepository.save(cartEntity);
            total_cart_amount = cartEntity.getCartAmount();
        }

        return new SaveCartResponse(productOptionId, total_cart_amount, addAmount, "장바구니에 성공적으로 저장되었습니다.");
    }
}
