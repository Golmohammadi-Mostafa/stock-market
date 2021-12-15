package com.payconiq.stockmarket.service.impl;


import com.payconiq.stockmarket.domain.Role;
import com.payconiq.stockmarket.domain.User;
import com.payconiq.stockmarket.dto.JwtTokenDTO;
import com.payconiq.stockmarket.dto.SingUpDTO;
import com.payconiq.stockmarket.dto.UserResponseDTO;
import com.payconiq.stockmarket.enums.RoleType;
import com.payconiq.stockmarket.enums.UserStatus;
import com.payconiq.stockmarket.exception.CustomException;
import com.payconiq.stockmarket.repository.UserRepository;
import com.payconiq.stockmarket.security.JwtTokenProvider;
import com.payconiq.stockmarket.service.RoleService;
import com.payconiq.stockmarket.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
    }

    @Override
    public JwtTokenDTO signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            return new JwtTokenDTO(token);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public JwtTokenDTO signUp(SingUpDTO singUpDTO) {


        if (!userRepository.existsByUsername(singUpDTO.getUsername())) {
            User user = new User();

            Set<RoleType> roleType = singUpDTO.getRoleType();
            Set<Role> roles = new HashSet<>();
            user.setUsername(singUpDTO.getUsername());
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(singUpDTO.getPassword()));

            userRepository.save(user);

            roleType.forEach(r -> {
                Optional<Role> role = roleService.findByName(r);
                role.ifPresent(roles::add);
            });

            User byUsername = userRepository.findByUsername(singUpDTO.getUsername());
            byUsername.setRoles(roles);
            User savedUser = userRepository.save(byUsername);

            String token = jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRoles());
            return new JwtTokenDTO(token);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserResponseDTO search(String username) {
        return whoAmI(username);
    }

    @Override
    public UserResponseDTO whoAmI(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        Set<Role> roles = user.getRoles();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setRoles(roles.stream().map(r -> r.getName().name()).collect(Collectors.toSet()));
        userResponseDTO.setUsername(user.getUsername());
        return userResponseDTO;
    }

    @Override
    public JwtTokenDTO refresh(String username) {
        String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        jwtTokenDTO.setToken(token);
        return jwtTokenDTO;
    }

    @Override
    public UserResponseDTO changeUserStatus(String username, UserStatus userStatus) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        userRepository.save(user);
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUsername(user.getUsername());
        return responseDTO;
    }


    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
