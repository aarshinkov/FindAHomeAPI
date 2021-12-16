package com.plamena.findahomeapi.services;

import com.plamena.findahomeapi.collections.EstatesCollection;
import com.plamena.findahomeapi.collections.ObjCollection;
import com.plamena.findahomeapi.entities.EstateEntity;
import com.plamena.findahomeapi.entities.UserEntity;
import com.plamena.findahomeapi.exceptions.FHException;
import com.plamena.findahomeapi.repositories.EstatesRepository;
import com.plamena.findahomeapi.repositories.UsersRepository;
import com.plamena.findahomeapi.requests.estates.EstateCreateRequest;
import com.plamena.findahomeapi.responses.estates.EstateEditRequest;
import com.plamena.findahomeapi.utils.Page;
import com.plamena.findahomeapi.utils.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EstateServiceImpl implements EstateService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private EstatesRepository estatesRepository;

  @Override
  public ObjCollection<EstateEntity> getEstates(Integer page, Integer limit) {

    try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
         CallableStatement cstmt = conn.prepareCall("{? = call get_estates(?, ?, ?)}")) {

      try {

        conn.setAutoCommit(false);

        cstmt.setInt(1, page);
        cstmt.setInt(2, limit);

        cstmt.registerOutParameter(3, Types.BIGINT);
        cstmt.registerOutParameter(4, Types.REF_CURSOR);

        cstmt.execute();

        Long globalCount = cstmt.getLong(3);

        ResultSet rset = (ResultSet) cstmt.getObject(4);

        ObjCollection<EstateEntity> collection = new EstatesCollection<>();

        List<EstateEntity> serviceExpenses = getEstatesFromResultSet(rset);

        collection.setData(serviceExpenses);

        long collectionCount = collection.getData().size();

        int start = (page - 1) * limit + 1;
        int end = start + collection.getData().size() - 1;

        Page pageWrapper = new PageImpl();
        pageWrapper.setCurrentPage(page);
        pageWrapper.setMaxElementsPerPage(limit);
        pageWrapper.setStartPage(start);
        pageWrapper.setEndPage(end);
        pageWrapper.setLocalTotalElements(collectionCount);
        pageWrapper.setGlobalTotalElements(globalCount);

        collection.setPage(pageWrapper);

        conn.commit();

        return collection;

      } catch (SQLException ex) {
        conn.rollback();
      } finally {
        conn.setAutoCommit(true);
      }

    } catch (Exception e) {
    }

    return null;
  }

  @Override
  public EstateEntity getEstate(String estateId) {
    return estatesRepository.findByEstateId(estateId);
  }

  @Override
  public EstateEntity createEstate(EstateCreateRequest ecr, String userId) {

    UserEntity user = usersRepository.findByUserId(userId);

    if (user == null) {
      throw new FHException(201, "User null", "User must not be null", HttpStatus.CONFLICT);
    }

    EstateEntity estate = new EstateEntity();
    estate.setEstateId(UUID.randomUUID().toString());
    estate.setName(ecr.getName());
    estate.setCountry(ecr.getCountry());
    estate.setCity(ecr.getCity());
    estate.setNeighborhood(ecr.getNeighborhood());
    estate.setPrice(ecr.getPrice());
    estate.setRooms(ecr.getRooms());
    estate.setFloor(ecr.getFloor());
    estate.setArea(ecr.getArea());
    estate.setAddress(ecr.getAddress());
    estate.setIsRent(ecr.getIsRent());
    estate.setCreatedOn(new Timestamp(System.currentTimeMillis()));
    estate.setUser(user);

    EstateEntity savedEstate = estatesRepository.save(estate);

    return savedEstate;
  }

  @Override
  public EstateEntity editEstate(EstateEditRequest eer, String userId) {

    EstateEntity storedEstate = estatesRepository.findByEstateId(eer.getEstateId());

    if (storedEstate == null) {
      throw new FHException(301, "Estate not found", "The estate with ID " + eer.getEstateId() + " was not found!", HttpStatus.NOT_FOUND);
    }

    storedEstate.setName(eer.getName());
    storedEstate.setCountry(eer.getCountry());
    storedEstate.setCity(eer.getCity());
    storedEstate.setNeighborhood(eer.getNeighborhood());
    storedEstate.setPrice(eer.getPrice());
    storedEstate.setRooms(eer.getRooms());
    storedEstate.setFloor(eer.getFloor());
    storedEstate.setArea(eer.getArea());
    storedEstate.setAddress(eer.getAddress());
    storedEstate.setIsRent(eer.getIsRent());
    storedEstate.setCreatedOn(storedEstate.getCreatedOn());
    storedEstate.setEditedOn(new Timestamp(System.currentTimeMillis()));
    storedEstate.setUser(storedEstate.getUser());

    EstateEntity editedEstate = estatesRepository.save(storedEstate);

    return editedEstate;
  }

  @Override
  public void deleteEstate(String estateId) {

    EstateEntity storedEstate = estatesRepository.findByEstateId(estateId);

    if (storedEstate == null) {
      throw new FHException(301, "Estate not found", "The estate with ID " + estateId + " was not found!", HttpStatus.NOT_FOUND);
    }

    estatesRepository.delete(storedEstate);
  }

  private List<EstateEntity> getEstatesFromResultSet(ResultSet rset) throws SQLException {

    List<EstateEntity> estates = new ArrayList<>();

    while (rset.next()) {
      EstateEntity estate = getEstateEntityFromResultSet(rset);
      estates.add(estate);
    }

    return estates;
  }

  private EstateEntity getEstateEntityFromResultSet(ResultSet rset) throws SQLException {

    EstateEntity estate = new EstateEntity();
    estate.setEstateId(rset.getString("estate_id"));
    estate.setName(rset.getString("name"));
    estate.setCountry(rset.getString("country"));
    estate.setCity(rset.getString("city"));
    estate.setNeighborhood(rset.getString("neighborhood"));
    estate.setPrice(rset.getDouble("price"));
    estate.setRooms(rset.getInt("rooms"));
    estate.setFloor(rset.getInt("floor"));
    estate.setArea(rset.getDouble("area"));
    estate.setAddress(rset.getString("address"));
    estate.setIsRent(rset.getBoolean("is_rent"));
    estate.setCreatedOn(rset.getTimestamp("created_on"));
    estate.setEditedOn(rset.getTimestamp("edited_on"));

    UserEntity user = usersRepository.findByUserId(rset.getString("user_id"));
    estate.setUser(user);

    return estate;
  }
}
