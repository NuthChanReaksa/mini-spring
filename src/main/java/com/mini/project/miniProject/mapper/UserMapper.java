package com.mini.project.miniProject.mapper;


import com.mini.project.miniProject.domain.Role;
import com.mini.project.miniProject.domain.User;
import com.mini.project.miniProject.features.user.dto.UserRequest;
import com.mini.project.miniProject.features.user.dto.UserResponse;
import com.mini.project.miniProject.features.user.dto.UserUpdateRequest;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Named("toUserResponse")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    @Mapping(target = "studentCardId", source = "studentIdCard")
    UserResponse toUserResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    //    define method that convert the set of role to set of string
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "studentIdCard", source = "studentCardId")
    User requestToUser(UserRequest userRequest);


//    @Mapping(target = "username",
//            source = "userRequest.username",
//            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
//            defaultExpression = "java(user.getUsername())")
    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(@MappingTarget User user, UserUpdateRequest userRequest);

}
