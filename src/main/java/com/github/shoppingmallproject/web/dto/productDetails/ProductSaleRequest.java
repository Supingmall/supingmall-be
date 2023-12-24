package com.github.shoppingmallproject.web.dto.productDetails;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductSaleRequest {
    private String productName;
    private String category;
    private String productPrice;
    private String finishAt;
    private List<ProductPhoto> productPhotos;
    private List<ProductDetailList> productDetailLists;
}
