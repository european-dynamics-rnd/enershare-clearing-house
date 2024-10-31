package com.enershare.repository.resource;

import com.enershare.model.resource.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {

    List<Resource> findByResourceId(String resourceId);

    @Query("SELECT r FROM Resource r ORDER BY r.createdOn DESC")
    List<Resource> findLatestResources(Pageable pageable);

    List<Resource> findByType(String type);
}
