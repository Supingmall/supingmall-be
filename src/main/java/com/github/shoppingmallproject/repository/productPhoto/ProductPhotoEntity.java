package com.github.shoppingmallproject.repository.productPhoto;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_photo")
public class ProductPhotoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_photo_id", nullable = false)
    private Integer productPhotoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "photo_url", length = 255, nullable = false)
    private String photoUrl;

    @Column(name = "photo_type", nullable = false)
    private Boolean photoType;

}