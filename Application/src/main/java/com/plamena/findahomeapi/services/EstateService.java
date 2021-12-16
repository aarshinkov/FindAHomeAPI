package com.plamena.findahomeapi.services;

import com.plamena.findahomeapi.collections.ObjCollection;
import com.plamena.findahomeapi.entities.EstateEntity;
import com.plamena.findahomeapi.requests.estates.EstateCreateRequest;
import com.plamena.findahomeapi.responses.estates.EstateEditRequest;

public interface EstateService {

  ObjCollection<EstateEntity> getEstates(Integer page, Integer limit);

  EstateEntity getEstate(String estateId);

  EstateEntity createEstate(EstateCreateRequest ecr, String userId);

  EstateEntity editEstate(EstateEditRequest eer, String userId);

  void deleteEstate(String estateId);
}
