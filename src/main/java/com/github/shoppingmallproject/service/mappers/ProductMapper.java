package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.web.dto.product.OptionDTO;
import com.github.shoppingmallproject.web.dto.product.PhotoDTO;
import com.github.shoppingmallproject.web.dto.product.ProductDetailResponse;
import com.github.shoppingmallproject.web.dto.product.SaleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "finishAt", expression = "java(strToTimeFormat(saleRequest.getFinishAt()))")
    @Mapping(target = "userEntity", source = "userEntity")
    @Mapping(target = "productOptionEntities", source = "productOptionEntity")
    @Mapping(target = "productPhotoEntities", source = "productPhotoEntity")
    @Mapping(target = "productStatus", expression = "java(\"판매중\")")
    ProductEntity saleRequestToProductEntity
            (SaleRequest saleRequest, UserEntity userEntity, List<ProductOptionEntity> productOptionEntity, List<ProductPhotoEntity> productPhotoEntity);

    @Mapping(target = "createAt", expression = "java(formatting(productEntity.getCreateAt()))")
    @Mapping(target = "finishAt", expression = "java(formatting(productEntity.getFinishAt()))")
    ProductDetailResponse productEntityToProductDetailResponse(ProductEntity productEntity);

    ProductOptionEntity OptionDTOToProductOptionEntity(OptionDTO optionDTO);

    ProductPhotoEntity PhotoDTOToProductPhotoEntity(PhotoDTO photoDTO);

    default LocalDateTime strToTimeFormat(String finishAt){
            return LocalDateTime.parse(finishAt+" 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    default String formatting(LocalDateTime localDateTime){
        if( localDateTime != null ){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH시 mm분");
            return localDateTime.format(dateTimeFormatter);
        }else return null;
    }
}
