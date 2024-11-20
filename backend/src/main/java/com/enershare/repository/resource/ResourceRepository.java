package com.enershare.repository.resource;

import com.enershare.model.resource.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String>, JpaSpecificationExecutor<Resource> {
    @Query("SELECT r from Resource r " +
            "JOIN User u on u.participantId = r.providerParticipantId" +
            " WHERE u.username = :username " +
            "ORDER BY r.createdOn DESC ")
    List<Resource> findLatestResources(@Param("username") String username, Pageable pageable);

    List<Resource> findByStatus(String type);

    @Query("SELECT r from Resource r " +
            "JOIN User u on u.participantId = r.providerParticipantId" +
            " WHERE u.username = :username " +
            "ORDER BY r.createdOn DESC ")
    List<Resource> findResourcesByUsername(@Param("username") String username);
}
