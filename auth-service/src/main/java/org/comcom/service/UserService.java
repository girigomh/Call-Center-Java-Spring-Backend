package org.comcom.service;

import org.comcom.dto.AddressDto;
import org.comcom.dto.UserDto;
import org.comcom.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserUpdateRequest request);

    UserDto updateUserAddress(AddressDto userAddressRequest);

    boolean deleteUserByEmail(String email, String authUser);
}
