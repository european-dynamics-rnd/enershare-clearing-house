package com.enershare.repository.logs;

import com.enershare.model.logs.Logs;
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
            "WHERE u.email = :email " +
            "AND l.mode = 'INGRESS'" +
            "ORDER BY l.createdOn DESC")
    List<Logs> findLatestIngressLogs(@Param("email") String email, Pageable pageable);

    @Query("SELECT l FROM Logs l " +
            "JOIN User u ON u.connectorUrl = l.provider " +
            "WHERE u.email = :email " +
            "AND l.mode = 'EGRESS'" +
            "ORDER BY l.createdOn DESC")
    List<Logs> findLatestEgressLogs(@Param("email") String email, Pageable pageable);

}


