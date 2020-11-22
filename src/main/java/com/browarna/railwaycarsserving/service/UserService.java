package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.UserDto;
import com.browarna.railwaycarsserving.mapper.UserMapper;
import com.browarna.railwaycarsserving.model.Role;
import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
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
        return userMapper.mapToDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(Instant.now());
        user.setInitials(getInitials(userDto));
        userRepository.save(user);
        return userMapper.mapToDto(userRepository.save(user));
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
        user.setInitials(getInitials(userDto));

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : userDto.getRoles()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        if (userDto.getPassword() != null && userDto.getPassword() != "" && !userDto.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find user by userId to delete"));
        userRepository.delete(user);
    }

    private String getInitials(UserDto userDto) {
        return userDto.getLastName() + " "
                + userDto.getFirstName().substring(0, 1) + "."
                + userDto.getMiddleName().substring(0, 1) + ".";
    }
}
