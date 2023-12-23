package com.github.shoppingmallproject.repository.productOption;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_option")
public class ProductOptionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id", nullable = false)
    private Integer productOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "color", length = 255)
    private String color;

    @Column(name = "product_size", length = 255)
    private String productSize;

    @Column(name = "stock", nullable = false, columnDefinition = "CHECK (stock >= 0)")
    private Integer stock;


}
