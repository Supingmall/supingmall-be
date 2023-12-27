package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.cart.CartEntity;
import com.github.shoppingmallproject.web.dto.cart.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "cartAmount", source = "cartAmount")
    @Mapping(target = "userId", source = "userEntity.userId") // UserEntity에서 userId 매핑
    @Mapping(target = "productOptionId", source = "productOptionEntity.productOptionId")
    Cart cartEntityToCart(CartEntity cartEntity);


}
