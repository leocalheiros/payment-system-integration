package com.leocalheiros.paymentsystem.controller;

import com.leocalheiros.paymentsystem.dto.UserCreateRequest;
import com.leocalheiros.paymentsystem.dto.UserResponse;
import com.leocalheiros.paymentsystem.entity.User;
import com.leocalheiros.paymentsystem.service.TokenService;
import com.leocalheiros.paymentsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    public UserController(UserService userService){
        this.userService = userService;
    }

    private final UserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register a user", description = "Register a user with name, email and password",
                    tags = {"User"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(description = "Email already exists", responseCode = "409", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserCreateRequest userCreateRequest) throws
            MessagingException, UnsupportedEncodingException {
        User user = userCreateRequest.toModel();
        UserResponse userSaved = userService.registerUser(user);

        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    @Operation(summary = "Check user registration using code sent by email",
            description = "Check user registration using code sent by email",
            tags = {"User"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity verifyUser(@RequestParam String code) {
        JSONObject response = userService.verify(code);

        if (response.getString("message").equals("verify_success")) {
            return ResponseEntity.ok(response.toString());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }
        }
    }
