package com.simbo.auth.dto;

import com.simbo.auth.document.Role;
import com.simbo.auth.document.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String id;
    private String email;
    private Role role;

    public static UserDTO from(User user) {
        return builder()
                .id(user.getId())
                .email(user.getUsername())
                .role(user.getRole())
                .build();
    }
}