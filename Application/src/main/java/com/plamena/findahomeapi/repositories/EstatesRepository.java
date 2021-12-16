package com.plamena.findahomeapi.repositories;

import com.plamena.findahomeapi.entities.EstateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatesRepository extends JpaRepository<EstateEntity, String> {

  EstateEntity findByEstateId(String estateId);
}
