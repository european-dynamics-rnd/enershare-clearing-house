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
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
    @Query("SELECT r from Resource r " +
    "JOIN User u on u.connectorUrl = r.providerConnectorId" +
    " WHERE u.email = :email " +
    "ORDER BY r.createdOn DESC ")
    List<Resource> findLatestResources(@Param("email") String email,Pageable pageable);

    List<Resource> findByStatus(String type);

    @Query("SELECT r from Resource r " +
    "JOIN User u on u.connectorUrl = r.providerConnectorId" +
    " WHERE u.email = :email " +
    "ORDER BY r.createdOn DESC ")
    List<Resource> findResourcesByUserEmail(@Param("email") String email);
}
