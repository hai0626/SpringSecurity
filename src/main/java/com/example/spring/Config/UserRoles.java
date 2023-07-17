package com.example.spring.Config;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.spring.Config.UserPermission.*;

public enum UserRoles {
    ADMIN(Sets.newHashSet(USER_READ,USER_WRITE,COURSE_READ,COURSE_WRITE)),
    USER(Sets.newHashSet()),
    ADMINTRAINEE(Sets.newHashSet(USER_READ,COURSE_READ));

    private final Set<UserPermission> permission;


    UserRoles(Set<UserPermission> permission) {
        this.permission = permission;
    }

    public Set<UserPermission> getPermission(){
        return permission;
    }

    public Set<SimpleGrantedAuthority> grantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermission().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
