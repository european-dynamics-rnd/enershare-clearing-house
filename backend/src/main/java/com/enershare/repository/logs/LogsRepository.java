package com.enershare.repository.logs;

import com.enershare.model.logs.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {

    @Query("SELECT l FROM Logs l ORDER BY l.createdOn DESC")
    List<Logs> findAllLogsOrderByCreatedOnDesc();
}