package com.enershare.repository.bid;

import com.enershare.model.bid.Bid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {

    List<Bid> findByStatus(String status);

    List<Bid> findByResourceId(String resourceId);

    @Query("SELECT b FROM Bid b ORDER BY b.createdOn DESC")
    List<Bid> findLatestBids(String string, Pageable pageable);
}
