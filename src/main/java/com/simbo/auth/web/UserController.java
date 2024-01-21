package com.simbo.auth.web;

import com.simbo.auth.document.Role;
import com.simbo.auth.document.User;
import com.simbo.auth.dto.UserDTO;
import com.simbo.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("#user.id == #id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> user(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok(UserDTO.from(userRepository.findById(id).orElseThrow()));
    }

////    @PreAuthorize("hasAuthority('ADMIN')")
//    @PatchMapping("/{id}/role")
//    public ResponseEntity<?> assignRole(@PathVariable String id, @RequestBody Role newRole) {
//        User user = userRepository.findById(id).orElseThrow(/* exception */);
//        user.setRole(newRole); // Assuming you have a setter for the role
//        userRepository.save(user);
//        return ResponseEntity.ok().build();
//    }

}
