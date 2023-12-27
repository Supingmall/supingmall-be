package com.github.shoppingmallproject.repository.cart;

import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartEntityJpaRepository extends JpaRepository<CartEntity, Integer> {

    CartEntity findByUserEntityAndProductOptionEntity(UserEntity userEntity, ProductOptionEntity productOptionEntity);
}
