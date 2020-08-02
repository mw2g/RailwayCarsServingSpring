package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.UserDto;
import com.browarna.railwaycarsserving.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target = "userId", source = "userDto.userId")
    @Mapping(target = "created", ignore = true)
    User map(UserDto userDto);

//    @Mapping(target = "auths", expression = "java(user.getRefreshTokens().stream().map(refreshToken -> refreshToken.getCreatedDate()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "auths", expression = "java(user.getRefreshTokens().stream().map(refreshToken -> new com.browarna.railwaycarsserving.dto.UserRefreshTokenDto(refreshToken.getId(), refreshToken.getCreatedDate())).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.toString()).collect(java.util.stream.Collectors.toList()))")
    UserDto mapToDto(User user);
}
