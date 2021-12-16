package com.plamena.findahomeapi.requests.estates;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstateCreateRequest implements Serializable {

  private String name;
  private String country;
  private String city;
  private String neighborhood;
  private Double price;
  private Integer rooms;
  private Integer floor;
  private Double area;
  private String address;
  private Boolean isRent;
}
