package com.mini.project.miniProject.features.user;


import com.mini.project.miniProject.features.user.dto.UserRequest;
import com.mini.project.miniProject.features.user.dto.UserResponse;
import com.mini.project.miniProject.features.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();

    UserResponse getUserById(String id);

    void deleteUserById(String id);

    UserResponse updateUserById(String id, UserUpdateRequest userRequest);

    UserResponse disableUser(String id);

    UserResponse enableUser(String id);

}
