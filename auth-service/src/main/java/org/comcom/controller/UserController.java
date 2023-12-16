package org.comcom.controller;

import lombok.AllArgsConstructor;
import org.comcom.dto.AddressDto;
import org.comcom.dto.ApiResponse;
import org.comcom.dto.UserUpdateRequest;
import org.comcom.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.comcom.utils.ApiResponseUtils.buildResponse;

@CrossOrigin
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> fetchAllUsers() {
        return buildResponse(userService.getAllUsers());
    }

    @GetMapping(path = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> fetchCurrentUser(@RequestHeader("x-auth-user") String authUser) {
        return buildResponse(userService.getUserByEmail(authUser));
    }

    @GetMapping(path = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> fetchUser(@PathVariable String email) {
        return buildResponse(userService.getUserByEmail(email));
    }


    @GetMapping(path = "/check-status", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> checkUserStatus(@RequestHeader("x-auth-user") String authUser) {
        return buildResponse(userService.getUserByEmail(authUser).getStatus());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return buildResponse(userService.updateUser(request));
    }

    @PutMapping(path = "/address", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> updateAddress(@RequestBody @Valid AddressDto addressDto) {
        return buildResponse(userService.updateUserAddress(addressDto));
    }

    @DeleteMapping(path = "/{email}",  produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> deleteUser(@PathVariable String email, @RequestHeader("x-auth-user") String authUser) {
        return buildResponse(userService.deleteUserByEmail(email, authUser));
    }
}
