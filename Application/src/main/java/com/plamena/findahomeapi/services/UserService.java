package com.plamena.findahomeapi.services;

import com.plamena.findahomeapi.entities.UserEntity;
import com.plamena.findahomeapi.requests.users.RoleAssignRequest;
import com.plamena.findahomeapi.requests.users.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

  UserEntity getUserByUserId(String userId);

  UserEntity getUserByEmail(String email);

  UserEntity createUser(UserCreateRequest ucr, List<RoleAssignRequest> roles);
}
