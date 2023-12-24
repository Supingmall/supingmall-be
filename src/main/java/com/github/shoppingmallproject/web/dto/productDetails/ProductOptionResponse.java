package com.github.shoppingmallproject.web.dto.productDetails;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductOptionResponse {
    private Integer optionId;
    private String color;
    private String size;
    private Integer price;
    private Integer quantity;
}
