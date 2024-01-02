package com.github.shoppingmallproject.repository.productPhoto;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPhotoJpa extends JpaRepository<ProductPhotoEntity, Integer> {
    ProductPhotoEntity findByProductEntity(ProductEntity productEntity);
}
