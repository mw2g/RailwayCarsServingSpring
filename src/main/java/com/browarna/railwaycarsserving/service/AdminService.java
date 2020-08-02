package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.UserDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.UserMapper;
import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream().map(user -> userMapper.mapToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    public UserDto getUserById(Long userId) {

        User user = userRepository.findById(userId).get();
        UserDto userDto = userMapper.mapToDto(user);
        return userDto;
    }

    public void createUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(Instant.now());
        userRepository.save(user);
    }

    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find user by userId to update"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setEnabled(userDto.isEnabled());
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());

        if (userDto.getPassword() != null && userDto.getPassword() != "") {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find user by userId to delete"));
        userRepository.delete(user);
    }
}
