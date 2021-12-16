package com.plamena.findahomeapi.services;

import com.plamena.findahomeapi.entities.RoleEntity;
import com.plamena.findahomeapi.entities.UserEntity;
import com.plamena.findahomeapi.repositories.RolesRepository;
import com.plamena.findahomeapi.repositories.UsersRepository;
import com.plamena.findahomeapi.requests.users.RoleAssignRequest;
import com.plamena.findahomeapi.requests.users.UserCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private RolesRepository rolesRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserEntity getUserByUserId(String userId) {

    UserEntity storedUser = usersRepository.findByUserId(userId);

    if (storedUser == null) {
      return null;
//      throw new UserServiceException(ErrorMessages.USER_NO_EXIST.getErrorMessage());
    }

    return storedUser;
  }

  @Override
  public UserEntity getUserByEmail(String email) {

    UserEntity storedUser = usersRepository.findByEmail(email);

    if (storedUser == null) {
      return null;
//      throw new UserServiceException(ErrorMessages.USER_NO_EXIST.getErrorMessage());
    }

    return storedUser;
  }

  @Override
  public UserEntity createUser(UserCreateRequest ucr, List<RoleAssignRequest> roles) {

    UserEntity user = new UserEntity();

    user.setUserId(UUID.randomUUID().toString());
    user.setEmail(ucr.getEmail());
    user.setPassword(passwordEncoder.encode(ucr.getPassword()));
    user.setFirstName(ucr.getFirstName());
    user.setLastName(ucr.getLastName());

    List<RoleEntity> roleEntities = new ArrayList<>();

    for (RoleAssignRequest role : roles) {
      roleEntities.add(rolesRepository.findByRole(role.getRole().toUpperCase()));
    }

    user.setRoles(roleEntities);

    UserEntity createdUser = usersRepository.save(user);

    return createdUser;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserEntity userEntity = usersRepository.findByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    List<GrantedAuthority> roles = new ArrayList<>();

    userEntity.getRoles().forEach((roleEntity) -> {
      roles.add(new SimpleGrantedAuthority(roleEntity.getRole()));
    });

    return new User(userEntity.getEmail(), userEntity.getPassword(), roles);
  }

}
