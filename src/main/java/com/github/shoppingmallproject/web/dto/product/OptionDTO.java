package com.github.shoppingmallproject.web.dto.product;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OptionDTO {
    private String color;
    private String productSize;
    private Integer stock;
}
