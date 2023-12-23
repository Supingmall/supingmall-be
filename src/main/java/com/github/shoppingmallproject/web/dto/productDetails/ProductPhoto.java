package com.github.shoppingmallproject.web.dto.productDetails;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductPhoto {
    private Integer productPhotoId;
    private Integer productId;
    private String photoUrl;
    private Boolean photoType;
}
