package com.plamena.findahomeapi.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "estates")
@DynamicInsert
@DynamicUpdate
public class EstateEntity implements Serializable {

  @Id
  @Column(name = "estate_id")
  private String estateId;

  @Column(name = "name")
  private String name;

  @Column(name = "country")
  private String country;

  @Column(name = "city")
  private String city;

  @Column(name = "neighborhood")
  private String neighborhood;

  @Column(name = "price")
  private Double price;

  @Column(name = "rooms")
  private Integer rooms;

  @Column(name = "floor")
  private Integer floor;

  @Column(name = "area")
  private Double area;

  @Column(name = "address")
  private String address;

  @Column(name = "is_rent")
  private Boolean isRent;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @Column(name = "edited_on")
  private Timestamp editedOn;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
