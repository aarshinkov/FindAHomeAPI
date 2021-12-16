package com.plamena.findahomeapi.controllers;

import com.plamena.findahomeapi.entities.UserEntity;
import com.plamena.findahomeapi.requests.users.RoleAssignRequest;
import com.plamena.findahomeapi.requests.users.UserCreateRequest;
import com.plamena.findahomeapi.responses.users.UserGetResponse;
import com.plamena.findahomeapi.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.plamena.findahomeapi.utils.Utils.getUserFromEntity;

@RestController
@Api(value = "Users", tags = "Users")
public class UsersController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @ApiOperation(value = "Get user")
  @GetMapping(value = "/api/v1/users/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserGetResponse> getUser(@PathVariable("identifier") String identifier) {

    UserEntity user = userService.getUserByUserId(identifier);
    if (user == null) {
      user = userService.getUserByEmail(identifier);
    }

    if (user == null) {
      return null;
    }

    UserGetResponse result = getUserFromEntity(user);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @ApiOperation(value = "Create user")
  @PostMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserGetResponse> createUser(@RequestBody UserCreateRequest ucr) {

    List<RoleAssignRequest> roles = new ArrayList<>();
    roles.add(new RoleAssignRequest("USER"));

    UserEntity createdUser = userService.createUser(ucr, roles);
    UserGetResponse result = getUserFromEntity(createdUser);

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

//  @GetMapping(value = "/password")
//  public String password() {
//    return passwordEncoder.encode("Test-1234");
//  }
}
