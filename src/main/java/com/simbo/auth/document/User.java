package com.simbo.auth.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Document
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private String role;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setRole(Role role) {
        this.role = role.name(); // Convert the enum to a string
    }

    public Role getRole() {
        try {
            return Role.valueOf(role);  // Convert the string back to an enum
        } catch (IllegalArgumentException | NullPointerException e) {
            // Handle the case where the role is not a valid enum value or is null
            return null; // or handle it in some other way
        }
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList();
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolePrefixed = "ROLE_" + this.role;
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(rolePrefixed));
        System.out.println("Authorities: " + authorities); // Add this log
        return authorities;
    }


}
