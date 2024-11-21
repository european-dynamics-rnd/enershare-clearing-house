package com.enershare.repository.bid;

import com.enershare.model.bid.BidAccepted;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidAcceptedRepository extends JpaRepository<BidAccepted, Long>, JpaSpecificationExecutor<BidAccepted> {

    List<BidAccepted> findByStatus(String status);

    List<BidAccepted> findByResourceId(String resourceId);

    @Query("SELECT b FROM BidAccepted b ORDER BY b.createdOn DESC")
    List<BidAccepted> findLatestBidsAccepted(Pageable pageable);

    @Query("SELECT b FROM BidAccepted b " +
            "JOIN User u ON u.participantId = b.consumerParticipantId " +
            "WHERE u.username = :username " +
            "ORDER BY b.createdOn DESC")
    List<BidAccepted> findBidsAcceptedByUsername(String username);

    @Query("SELECT b FROM BidAccepted b ORDER BY b.createdOn DESC")
    List<BidAccepted> findAllBidsAcceptedSortedByCreatedOn();
}
