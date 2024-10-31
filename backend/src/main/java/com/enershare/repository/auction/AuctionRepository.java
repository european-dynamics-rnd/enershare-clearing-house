package com.enershare.repository.auction;

import com.enershare.model.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    List<Auction> findByStatus(String status);

    List<Auction> findByResourceId(String resourceId);

    @Query("SELECT a FROM Auction a ORDER BY a.createdOn DESC")
    List<Auction> findLatestAuctions(String string, Pageable pageable);

}
