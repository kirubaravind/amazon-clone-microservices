package com.amz.user_service.service;

import com.amz.user_service.exception.InvalidCredentialsException;
import com.amz.user_service.exception.UserAlreadyExistsException;
import com.amz.user_service.model.User;
import com.amz.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException("Username already taken");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);

    }

    public User validateLogin(String username, String rawPassword){
        User user= userRepository.findByUsername(username)
                .orElseThrow(()->new InvalidCredentialsException("Invalid Username or password"));

        if(!passwordEncoder.matches(rawPassword,user.getPassword())){
            throw new InvalidCredentialsException("Invalid Username or password");
        }
        return user;


    }

}
