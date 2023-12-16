package org.comcom.service.implementation;

import lombok.AllArgsConstructor;
import org.comcom.dto.AddressDto;
import org.comcom.dto.UserDto;
import org.comcom.dto.UserUpdateRequest;
import org.comcom.exception.ForbiddenException;
import org.comcom.exception.IncorrectCredentialsException;
import org.comcom.exception.NotFoundException;
import org.comcom.model.Users;
import org.comcom.repository.UserRepository;
import org.comcom.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.comcom.config.security.SecurityUtils.getCurrentUser;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
            .orElseThrow(IncorrectCredentialsException::new)
            .toDto();
    }

    @Override
    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream().map(Users::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request) {
        String currentUserEmail = getCurrentUser().getUsername();
        Users userEntity = userRepository.findByEmailIgnoreCase(currentUserEmail).get();

        BeanUtils.copyProperties(request, userEntity);
        userEntity.setUsername(request.getFirstName() + " " + request.getLastName());
        userEntity.setUpdatedOn(LocalDateTime.now());
        return userRepository.save(userEntity).toDto();
    }

    @Override
    public UserDto updateUserAddress(AddressDto userAddressRequest) {
        String currentUserEmail = getCurrentUser().getUsername();
        Users userEntity = userRepository.findByEmailIgnoreCase(currentUserEmail).get();

        BeanUtils.copyProperties(userAddressRequest, userEntity);
        userEntity.setUpdatedOn(LocalDateTime.now());
        return userRepository.save(userEntity).toDto();
    }

    @Override
    public boolean deleteUserByEmail(String email, String authUser) {
        Users userEntity = userRepository.findByEmailIgnoreCase(authUser).get();

        Optional<Users> checkUser = userRepository.findByEmailIgnoreCase(email);

        if(checkUser.isPresent()){
            String role = userEntity.getProfile().getName();

            switch (role.toUpperCase()){
                case "COMPANY_LEADER", "SWITCHING_CENTER_LEADER", "ADMIN" -> {
                   userRepository.deleteById(checkUser.get().getId());
                    return true;
                }
                default -> throw new ForbiddenException("Not Authorised to delete user", "Unauthorized to delete user");
            }
        }else{
            throw new NotFoundException("User not found", "User not found: "+email);
        }

    }
}
