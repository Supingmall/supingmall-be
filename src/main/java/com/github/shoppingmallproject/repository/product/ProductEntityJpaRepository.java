package com.github.shoppingmallproject.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntityJpaRepository extends JpaRepository<ProductEntity, Integer> {
}
