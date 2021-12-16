package com.plamena.findahomeapi.controllers;

import com.plamena.findahomeapi.collections.EstatesCollection;
import com.plamena.findahomeapi.collections.ObjCollection;
import com.plamena.findahomeapi.entities.EstateEntity;
import com.plamena.findahomeapi.exceptions.FHException;
import com.plamena.findahomeapi.requests.estates.EstateCreateRequest;
import com.plamena.findahomeapi.responses.estates.EstateEditRequest;
import com.plamena.findahomeapi.responses.estates.EstateGetResponse;
import com.plamena.findahomeapi.services.EstateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.plamena.findahomeapi.utils.Utils.getEstateFromEntity;
import static com.plamena.findahomeapi.utils.Utils.getEstatesResponseFromEntity;

@RestController
@Api(value = "Estates", tags = "Estates")
public class EstatesController {

  @Autowired
  private EstateService estateService;

  @ApiOperation(value = "Get estates")
  @GetMapping(value = "/api/v1/estates", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ObjCollection<EstateGetResponse>> getEstates(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(value = "limit", defaultValue = "6") Integer limit) {

    if (page <= 0) {
      page = 1;
    }

    if (limit <= 0) {
      limit = 6;
    }

    ObjCollection<EstateEntity> estates = estateService.getEstates(page, limit);
    ObjCollection<EstateGetResponse> result = new EstatesCollection<>();
    result.setPage(estates.getPage());
    List<EstateGetResponse> estatesCollection = getEstatesResponseFromEntity(estates.getData());
    result.setData(estatesCollection);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @ApiOperation(value = "Get estate")
  @GetMapping(value = "/api/v1/estates/{estateId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EstateGetResponse> getEstate(@PathVariable("estateId") String estateId) {

    EstateEntity estate = estateService.getEstate(estateId);
    EstateGetResponse result = getEstateFromEntity(estate);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @ApiOperation(value = "Create estate")
  @PostMapping(value = "/api/v1/estates", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EstateGetResponse> createEstate(@RequestBody EstateCreateRequest ecr,
                                                        @RequestParam(name = "userId") String userId) {

    EstateEntity createdEstate = estateService.createEstate(ecr, userId);
    EstateGetResponse result = getEstateFromEntity(createdEstate);

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Edit estate")
  @PutMapping(value = "/api/v1/estates", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<EstateGetResponse> editEstate(@RequestBody EstateEditRequest eer,
                                                      @RequestParam(name = "userId") String userId) {

    EstateEntity editedEstate = estateService.editEstate(eer, userId);
    EstateGetResponse result = getEstateFromEntity(editedEstate);

    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Delete estate")
  @DeleteMapping(value = "/api/v1/estates/{estateId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> deleteEstate(@PathVariable("estateId") String estateId) {

    try {
      estateService.deleteEstate(estateId);
    } catch (FHException e) {
      return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
    }

    return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
  }
}
