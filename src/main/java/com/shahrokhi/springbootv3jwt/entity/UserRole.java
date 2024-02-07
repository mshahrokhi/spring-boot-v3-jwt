package com.shahrokhi.springbootv3jwt.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.shahrokhi.springbootv3jwt.entity.UserRolePermission.*;

@RequiredArgsConstructor
public enum UserRole {

  ADMIN(
          Set.of(
                  ADMIN_CREATE,
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE
          )
  ),
  CLIENT(
          Set.of(
                  CLIENT_CREATE,
                  CLIENT_READ,
                  CLIENT_UPDATE,
                  CLIENT_DELETE
          )
  );


  @Getter
  private final Set<UserRolePermission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
