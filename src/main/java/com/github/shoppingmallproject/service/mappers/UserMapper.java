package com.github.shoppingmallproject.service.mappers;

import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.web.dto.AccountDTO;
import com.github.shoppingmallproject.web.dto.LoginRequest;
import com.github.shoppingmallproject.web.dto.SignUpRequest;
import com.github.shoppingmallproject.web.dto.SignUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserEntity signUpRequestToUserEntity(SignUpRequest signUpRequest);
    @Mapping(target = "createdAt", expression = "java(formatting(userEntity.getCreatedAt()))")
    SignUpResponse userEntityToSignUpResponse(UserEntity userEntity);

    @Mapping(target = "userRoles", source = "roles")
    AccountDTO userEntityToAccountDTO(UserEntity userEntity, List<String> roles);
    default String formatting(LocalDateTime localDateTime){
        if( localDateTime != null ){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH시 mm분");
            return localDateTime.format(dateTimeFormatter);
        }else return null;
    }
    default String setDefault(String imageUrl){
        return imageUrl==null ? "url" : imageUrl;
    }

    default List<String> rolesMapper(UserEntity userEntity){
        return userEntity.getUserRoles().stream()
                .map(ur->ur.getRoles()).map(r->r.getName()).toList();
    }
}
