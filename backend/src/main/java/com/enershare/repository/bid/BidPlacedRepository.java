package com.enershare.repository.bid;

import com.enershare.model.bid.BidPlaced;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidPlacedRepository extends JpaRepository<BidPlaced, Long>, JpaSpecificationExecutor<BidPlaced> {

    List<BidPlaced> findByStatus(String status);

    List<BidPlaced> findByResourceId(String resourceId);

    @Query("SELECT b FROM BidPlaced b ORDER BY b.createdOn DESC")
    List<BidPlaced> findLatestBidsPlaced(Pageable pageable);

    @Query("SELECT b FROM BidPlaced b " +
            "JOIN User u ON u.participantId = b.providerParticipantId " +
            "WHERE u.username = :username " +
            "ORDER BY b.createdOn DESC")
    List<BidPlaced> findBidsPlacedByUsername(String username);

    @Query("SELECT b FROM BidPlaced b ORDER BY b.createdOn DESC")
    List<BidPlaced> findAllBidsPlacedSortedByCreatedOn();
}
