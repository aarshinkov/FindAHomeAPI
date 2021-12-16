package com.plamena.findahomeapi.controllers;

import com.plamena.findahomeapi.entities.UserEntity;
import com.plamena.findahomeapi.exceptions.AuthException;
import com.plamena.findahomeapi.requests.AuthenticationRequest;
import com.plamena.findahomeapi.responses.AuthenticationResponse;
import com.plamena.findahomeapi.responses.users.UserGetResponse;
import com.plamena.findahomeapi.security.JwtUtil;
import com.plamena.findahomeapi.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.plamena.findahomeapi.utils.Utils.getUserFromEntity;

@RestController
@Api(value = "Authentication", tags = "Authentication")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @ApiOperation(value = "Login")
  @PostMapping(value = "/api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest auth) {

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
      final UserDetails userDetails = userService.loadUserByUsername(auth.getEmail());
      final String jwt = jwtUtil.generateToken(userDetails);
      AuthenticationResponse response = new AuthenticationResponse();
      response.setTokenType("Bearer");
      response.setAccessToken(jwt);

      List<String> authorities = new ArrayList<>();

      userDetails.getAuthorities().forEach((authority) -> {
        authorities.add(authority.getAuthority());
      });

      response.setAccess(authorities);

      UserEntity storedUser = userService.getUserByEmail(auth.getEmail());

      UserGetResponse user = getUserFromEntity(storedUser);

      response.setUser(user);

      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (BadCredentialsException e) {
      AuthException authException = new AuthException(1001, "Bad credentials", "The user entered wrong email or password");
      throw authException;
    }
  }
}
