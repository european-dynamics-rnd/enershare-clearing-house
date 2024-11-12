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
public interface PurchaseRepository extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {

    @Query("SELECT p from Purchase p " +
            "JOIN User u on u.participantId = p.consumerParticipantId" +
            " WHERE u.email = :email " +
            "ORDER BY p.createdOn DESC ")
    List<Purchase> findPurchasedByUserEmail(@Param("email") String email);

    @Query("SELECT p from Purchase p " +
            "JOIN User u on u.participantId = p.consumerParticipantId" +
            " WHERE u.email = :email " +
            "ORDER BY p.createdOn DESC ")
    List<Purchase> findLatestPurchases(@Param("email") String email, Pageable pageable);
}
