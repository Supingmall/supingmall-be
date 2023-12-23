package com.github.shoppingmallproject.repository.productOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionEntity, Integer> {
}
