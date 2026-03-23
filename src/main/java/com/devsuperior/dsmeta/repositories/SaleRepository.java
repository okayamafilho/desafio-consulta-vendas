package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, obj.seller.name) "
          + "FROM Sale obj "
          + "WHERE obj.date BETWEEN :minDate AND :maxDate " 
          + "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name,  '%'))")
    Page<SaleMinDTO> searchByFilters(
        @Param("minDate") LocalDate minDate, 
        @Param("maxDate") LocalDate maxDate,
        @Param("name") String name, 
        Pageable pageable         
    );         

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumDTO(obj.seller.name, SUM(obj.amount)) " 
          + "FROM Sale obj " 
          + "WHERE obj.date BETWEEN :minDate AND :maxDate " 
          + "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')) "
          + "GROUP BY obj.seller.name")
    List<SaleSumDTO> summaryByFilters(
        @Param("minDate") LocalDate minDate, 
        @Param("maxDate") LocalDate maxDate, 
        @Param("name") String name
    );          
           



}
