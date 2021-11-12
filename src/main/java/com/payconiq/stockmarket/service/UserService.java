package com.payconiq.stockmarket.service;


import com.payconiq.stockmarket.dto.JwtTokenDTO;
import com.payconiq.stockmarket.dto.SingUpDTO;
import com.payconiq.stockmarket.dto.UserResponseDTO;
import com.payconiq.stockmarket.enums.UserStatus;

public interface UserService {
    JwtTokenDTO signIn(String username, String password);

    JwtTokenDTO signUp(SingUpDTO dto);

    void delete(String username);

    UserResponseDTO search(String username);

    UserResponseDTO whoAmI(String userName);

    JwtTokenDTO refresh(String username);

    UserResponseDTO changeUserStatus(String username, UserStatus userStatus);

    void deleteAllUsers();
}
