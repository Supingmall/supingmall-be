package com.github.shoppingmallproject.web.dto.productDetails;

import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDetailResponse {
    private Integer productId;
    private String productName;
//    private String photoUrl;
    private Integer productPrice;
    private String category;
    private String productStatus;
    private String createAt;
    private String finishAt;
    // 별점 평균 대기
    private Float scoreAvg;
    private List<ProductPhoto> productPhoto;
    private List<ProductDetailList> productDetailList;
    private List<ProductReview> productReview;

}
