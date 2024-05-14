package com.enershare.repository.logs;

import com.enershare.model.logs.Logs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {

    @Query("SELECT l FROM Logs l ORDER BY l.createdOn DESC")
    List<Logs> findAllLogsOrderByCreatedOnDesc();

    @Query("SELECT l FROM Logs l " +
            "WHERE (l.consumer = :email OR l.provider = :email) " +
            "AND l.stage = 'INGRESS'")
    Page<Logs> getIngressLogsByEmail(String email, Pageable pageable);


    @Query("SELECT l FROM Logs l " +
            "WHERE l.consumer = :email " +
            "AND l.stage = 'EGRESS'")
    Page<Logs> getEgressLogsByEmail(String email, Pageable pageable);

    @Query("SELECT COUNT(l) FROM Logs l WHERE l.provider = :email AND l.stage = 'INGRESS'")
    long countIngressLogsByEmail(@Param("email") String email);


    @Query("SELECT COUNT(l) FROM Logs l WHERE l.consumer = :email AND l.stage = 'EGRESS'")
    long countEgressLogsByEmail(@Param("email") String email);


    @Query("SELECT l FROM Logs l WHERE l.stage = 'INGRESS' ORDER BY l.createdOn DESC")
    List<Logs> findLatestIngressLogs(Pageable pageable);

    @Query("SELECT l FROM Logs l WHERE l.stage = 'EGRESS' ORDER BY l.createdOn DESC")
    List<Logs> findLatestEgressLogs(Pageable pageable);
}
