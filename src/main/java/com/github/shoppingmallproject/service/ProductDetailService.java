package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductEntityJpaRepository;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpaRepository;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoJpaRepository;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.review.ReviewEntityJpaRepository;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.ProductMapper;
import com.github.shoppingmallproject.web.dto.productDetails.ProductDetailList;
import com.github.shoppingmallproject.web.dto.productDetails.ProductDetailResponse;
import com.github.shoppingmallproject.web.dto.productDetails.ProductPhoto;
import com.github.shoppingmallproject.web.dto.productDetails.ProductReview;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductEntityJpaRepository productEntityJpaRepository;
    private final ProductOptionJpaRepository productOptionJpaRepository;
    private final ProductPhotoJpaRepository productPhotoJpaRepository;
    private final ReviewEntityJpaRepository reviewEntityJpaRepository;


            public ProductDetailResponse getProductDetails(Integer productId) {

                ProductEntity productEntity = productEntityJpaRepository.findById(productId)
                        .orElseThrow(()->new NotFoundException("상품을 찾을 수 없습니다."));

                ProductDetailResponse productDetailResponse = ProductMapper.INSTANCE.productEntityToProductDetailResponse(productEntity);
                List<ProductPhotoEntity> productPhotoEntities = productEntity.getProductPhotoEntities();
                List<ProductOptionEntity> productOptionEntities = productEntity.getProductOptionEntities();
                List<ProductDetailList> productDetailLists = productOptionEntities.stream()
                        .map(po->{
                            return ProductDetailList.builder()
                                    .color(po.getColor())
                                    .stock(po.getStock())
                                    .productSize(po.getProductSize())
                                    .build();
                        })
                        .toList();
                List<ProductPhoto> productPhotos =  productPhotoEntities.stream().map(pp->{
                    return ProductPhoto.builder().photoUrl(pp.getPhotoUrl())
                            .photoType(pp.getPhotoType())
                            .build();
                }).toList();
                productDetailResponse.setProductPhoto(productPhotos);
                productDetailResponse.setProductDetailList(productDetailLists);

                List<ReviewEntity> reviewEntities = productEntity.getReviewEntities();
                List<ProductReview> productReviews = reviewEntities.stream()
                        .map(re->{
                            return ProductReview.builder()
                                    .score(re.getScore())
                                    .nickName(re.getUserEntity().getNickName())
                                    .reviewContents(re.getReviewContents())
                                    .createAt(re.getCreateAt())
                                    .build();
                        }).toList();
                productDetailResponse.setProductReview(productReviews);
                productDetailResponse.setScoreAvg(productEntity.getRating());



                return productDetailResponse;

            }
        }