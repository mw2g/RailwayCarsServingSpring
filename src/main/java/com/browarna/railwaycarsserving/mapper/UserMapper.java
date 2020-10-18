package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.UserDto;
import com.browarna.railwaycarsserving.dto.UserRefreshTokenDto;
import com.browarna.railwaycarsserving.model.RefreshToken;
import com.browarna.railwaycarsserving.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
//    @Mapping(target = "userId", source = "userDto.userId")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "refreshTokens", ignore = true)
    @Mapping(target = "initials", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    public abstract User map(UserDto userDto);

//    @Mapping(target = "auths", expression = "java(user.getRefreshTokens().stream().map(refreshToken -> refreshToken.getCreatedDate()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "auths", expression = "java(mapToDtoAuths(user))")
    @Mapping(target = "roles", expression = "java(mapToDtoRoles(user))")
    public abstract UserDto mapToDto(User user);

    List<UserRefreshTokenDto> mapToDtoAuths(User user) {
        if (user.getRefreshTokens() == null) {
            return null;
        }
        return user.getRefreshTokens().stream()
                .map(refreshToken -> new UserRefreshTokenDto(refreshToken.getId(), refreshToken.getCreatedDate()))
                .collect(java.util.stream.Collectors.toList());
    }

    List<String> mapToDtoRoles(User user) {
        if (user.getRoles() == null) {
            return null;
        }
        return user.getRoles().stream().map(role -> role.toString()).collect(java.util.stream.Collectors.toList());
    }
}
