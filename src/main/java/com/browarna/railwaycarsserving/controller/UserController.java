package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.UserDto;
import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.service.AdminService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
public class UserController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.status(OK)
                .body(adminService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(OK)
                .body(adminService.getUserById(userId));
    }

    @PostMapping
    public String createUser(@RequestBody UserDto userDto) {
        adminService.createUser(userDto);
        return new JSONObject().put("message", "Пользователь создан").toString();
    }

    @PutMapping
    public String updateUser(@RequestBody UserDto userDto) {
        adminService.updateUser(userDto);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return new JSONObject().put("message", "Пользователь удален").toString();
    }
}
