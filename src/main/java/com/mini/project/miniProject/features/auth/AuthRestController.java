package com.mini.project.miniProject.features.auth;


import com.mini.project.miniProject.features.auth.dto.AuthRequest;
import com.mini.project.miniProject.features.auth.dto.AuthResponse;
import com.mini.project.miniProject.features.auth.dto.RefreshTokenRequest;
import com.mini.project.miniProject.features.user.UserService;
import com.mini.project.miniProject.features.user.dto.UserRequest;
import com.mini.project.miniProject.features.user.dto.UserResponse;
import com.mini.project.miniProject.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@SecurityRequirements(value = {})
public class AuthRestController {
    private final AuthServiceImpl authService;
    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<AuthResponse> login(@RequestBody AuthRequest request){
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.login(request));
    }

    @PostMapping("/refresh")
    public BaseResponse<AuthResponse> refresh(@RequestBody RefreshTokenRequest request){
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.refreshToken(request));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register new user"
            , requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = UserRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "username": "chanreaksa",
                              "fullName": "string",
                              "gender": "male",
                              "pin": "898989",
                              "email": "sokkhann@gmail.com",
                              "password": "string",
                              "profileImage": "string",
                              "phoneNumber": "string",
                              "cityOrProvince": "string",
                              "khanOrDistrict": "string",
                              "sangkatOrCommune": "string",
                              "employeeType": "string",
                              "companyName": "string",
                              "mainSourceOfIncome": "string",
                              "monthlyIncomeRange": 0,
                              "studentCardId": "string",
                              "roles": [
                                "ADMIN","STUFF"
                              ]
                            }                              
                            """)

            )
    )
    )
    public BaseResponse<UserResponse> registerUser(
            @Valid @RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess()
                .setPayload(userService.createUser(userRequest));
    }




}
