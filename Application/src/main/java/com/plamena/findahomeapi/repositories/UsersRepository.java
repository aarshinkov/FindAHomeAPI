package com.plamena.findahomeapi.repositories;

import com.plamena.findahomeapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUserId(String userId);

  UserEntity findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUserId(String userId);
}
