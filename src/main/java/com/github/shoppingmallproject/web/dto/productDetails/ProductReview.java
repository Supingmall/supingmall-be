package com.github.shoppingmallproject.web.dto.productDetails;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductReview {
    private Integer reviewId;
    private Integer userId;
    private Integer productId;
    private String nickName;
    private String reviewContents;
    private LocalDateTime createAt;
    private Integer score;

}
