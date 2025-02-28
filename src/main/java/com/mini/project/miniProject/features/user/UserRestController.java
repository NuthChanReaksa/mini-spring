package com.mini.project.miniProject.features.user;



import com.mini.project.miniProject.features.user.dto.UserResponse;
import com.mini.project.miniProject.features.user.dto.UserUpdateRequest;
import com.mini.project.miniProject.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor

//provide default values for requestbody and params
public class UserRestController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public BaseResponse<List<UserResponse>> getAllUser() {
        return BaseResponse.<List<UserResponse>>ok()
                .setPayload(userService.getAllUsers());

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public BaseResponse<UserResponse> getUserById(@PathVariable String id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.getUserById(id));
    }


    @GetMapping("/me")
    public BaseResponse<UserResponse> getCurrentUserInfo(@AuthenticationPrincipal Jwt jwt) {

        System.out.println("These are the information extracted from jwt : ");
        System.out.println(jwt.getSubject());
        System.out.println(jwt.getTokenValue());
//        System.out.println(jwt.getIssuer());

        return BaseResponse.<UserResponse>ok()
                .setPayload(
                        userService.getUserById(jwt.getId())
                );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse<?> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return BaseResponse.ok();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user by id")
    // configure swagger to provide the default request body for updating the user
    public BaseResponse<UserResponse> updateUserByID(
            @PathVariable() String id, @RequestBody UserUpdateRequest userRequest) {
        return BaseResponse.<UserResponse>updateSuccess()
                .setPayload(userService.updateUserById(id, userRequest));
    }

    @PatchMapping("/{id}/disable")
    public BaseResponse<UserResponse> disableUser(@PathVariable() String id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.disableUser(id));
    }

    @PatchMapping("/{id}/enable")
    public BaseResponse<UserResponse> enableUser(@PathVariable String id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.enableUser(id));
    }

}
