package com.plamena.findahomeapi.repositories;

import com.plamena.findahomeapi.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, String> {

  RoleEntity findByRole(String role);
}
