package com.enershare.repository.purchase;

import com.enershare.model.purchase.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase>, CustomPurchaseRepository {

    List<Purchase> findPurchaseByResourceId(String resourceId);

    @Query("SELECT p from Purchase p " +
            "JOIN User u on u.participantId = p.consumerParticipantId" +
            " WHERE u.username = :username " +
            "ORDER BY p.createdOn DESC ")
    List<Purchase> findPurchasedByUsername(@Param("username") String username);

    @Query("SELECT p from Purchase p " +
            "JOIN User u on u.participantId = p.consumerParticipantId" +
            " WHERE u.username = :username " +
            "ORDER BY p.createdOn DESC ")
    List<Purchase> findLatestPurchases(@Param("username") String username, Pageable pageable);


    @Query("SELECT p FROM Purchase p " +
            "JOIN Resource r on r.resourceId = p.resourceId " +
            "JOIN User  u on u.participantId = r.providerParticipantId " +
            "WHERE u.username = :username")
    List<Purchase> findLatestPurchasedResources(@Param("username") String username, Pageable pageable);

}
