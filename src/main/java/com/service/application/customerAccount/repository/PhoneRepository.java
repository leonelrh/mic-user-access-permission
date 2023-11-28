package com.service.application.customerAccount.repository;

import com.service.application.customerAccount.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhoneRepository  extends JpaRepository<Phone, Integer> {

    List<Phone> getPhonesByIdUser(String IdUser);


    @Query(value = "DELETE FROM phone WHERE id_user = :idUser", nativeQuery = true)
    void deleteByUserId(@Param("idUser") String idUser);

}