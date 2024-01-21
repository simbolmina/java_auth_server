package com.simbo.auth.dto;

import com.simbo.auth.document.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUpdateDTO {
    private Role role;
}
