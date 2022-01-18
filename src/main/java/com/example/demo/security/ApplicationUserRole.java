package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.security.ApplicationUserPermission.*;

@Getter
@AllArgsConstructor
public enum ApplicationUserRole {
    STUDENT(Set.of()),
    ADMIN(Set.of(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
    ADMINTRAINEE(Set.of(STUDENT_READ, COURSE_READ));

    private final Set<ApplicationUserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
