package com.github.shoppingmallproject.repository.product;

import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.web.dto.productDetails.ProductDetailList;
import com.github.shoppingmallproject.web.dto.productDetails.ProductPhoto;
import com.github.shoppingmallproject.web.dto.productDetails.ProductReview;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class ProductEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;


    @Column(name = "product_name", length = 100, nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "category", length = 30, nullable = false,
            columnDefinition = "CHECK (category IN ('상의', '하의', '신발'))")
    private String category;

    @Column(name = "product_status", length = 30, nullable = false,
            columnDefinition = "VARCHAR(30) DEFAULT '판매중' CHECK(product_status IN('판매중', '판매완료'))")
    private String productStatus;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "finish_at")
    private LocalDateTime finishAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductPhotoEntity> productPhotoEntities;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductOptionEntity> productOptionEntities;

    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviewEntities;


}
