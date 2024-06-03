package com.enershare.repository.logs;

import com.enershare.filtering.specification.LogsSpecification;
import com.enershare.model.logs.Logs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long>, CustomLogsRepository, JpaSpecificationExecutor<Logs> {

    @Query("SELECT l FROM Logs l ORDER BY l.createdOn DESC")
    List<Logs> findAllLogsOrderByCreatedOnDesc();

    @Query("SELECT l FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.consumer " +
            "WHERE u.email = :email")
    Page<Logs> getIngressLogsByEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT l FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.provider " +
            "WHERE u.email = :email ")
    Page<Logs> getEgressLogsByEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT COUNT(l) FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.consumer " +
            "WHERE u.email = :email ")
    long countIngressLogsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(l) FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.provider " +
            "WHERE u.email = :email ")
    long countEgressLogsByEmail(@Param("email") String email);

    @Query("SELECT l FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.consumer " +
            "WHERE u.email = :email " +
            "ORDER BY l.createdOn DESC")
    List<Logs> findLatestIngressLogs(@Param("email") String email, Pageable pageable);

    @Query("SELECT l FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.provider " +
            "WHERE u.email = :email " +
            "ORDER BY l.createdOn DESC")
    List<Logs> findLatestEgressLogs(@Param("email") String email, Pageable pageable);
    
}


