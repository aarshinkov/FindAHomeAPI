package com.plamena.findahomeapi.utils;

import com.plamena.findahomeapi.entities.EstateEntity;
import com.plamena.findahomeapi.entities.RoleEntity;
import com.plamena.findahomeapi.entities.UserEntity;
import com.plamena.findahomeapi.responses.estates.EstateGetResponse;
import com.plamena.findahomeapi.responses.users.RoleGetResponse;
import com.plamena.findahomeapi.responses.users.UserGetResponse;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static UserGetResponse getUserFromEntity(UserEntity user) {
    UserGetResponse ugr = new UserGetResponse();
    ugr.setUserId(user.getUserId());
    ugr.setEmail(user.getEmail());
    ugr.setFirstName(user.getFirstName());
    ugr.setLastName(user.getLastName());
    ugr.setCreatedOn(user.getCreatedOn());

    List<RoleGetResponse> roles = new ArrayList<>();

    for (RoleEntity storedRole : user.getRoles()) {
      RoleGetResponse role = new RoleGetResponse();
      role.setRole(storedRole.getRole());
      roles.add(role);
    }

    ugr.setRoles(roles);

    return ugr;
  }

  public static List<EstateGetResponse> getEstatesResponseFromEntity(List<EstateEntity> estates) {

    List<EstateGetResponse> result = new ArrayList<>();

    for (EstateEntity estate : estates) {
      EstateGetResponse estateGetResponse = getEstateFromEntity(estate);
      result.add(estateGetResponse);
    }

    return result;
  }

  public static EstateGetResponse getEstateFromEntity(EstateEntity estate) {

    EstateGetResponse egr = new EstateGetResponse();
    egr.setEstateId(estate.getEstateId());
    egr.setName(estate.getName());
    egr.setCountry(estate.getCountry());
    egr.setCity(estate.getCity());
    egr.setNeighborhood(estate.getNeighborhood());
    egr.setPrice(estate.getPrice());
    egr.setRooms(estate.getRooms());
    egr.setFloor(estate.getFloor());
    egr.setArea(estate.getArea());
    egr.setAddress(estate.getAddress());
    egr.setIsRent(estate.getIsRent());
    egr.setCreatedOn(estate.getCreatedOn());
    egr.setEditedOn(estate.getEditedOn());

    UserGetResponse ugr = getUserFromEntity(estate.getUser());
    egr.setUser(ugr);

    return egr;
  }
}
