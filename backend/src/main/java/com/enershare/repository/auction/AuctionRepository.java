package com.enershare.repository.auction;

import com.enershare.model.auction.Auction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    List<Auction> findByStatus(String status);

    List<Auction> findByResourceId(String resourceId);

    Optional<Auction> findByHash(String hash);

    @Query("SELECT a from Auction a " +
            "JOIN User u on u.participantId = a.providerParticipantId" +
            " WHERE u.username = :username " +
            "ORDER BY a.createdOn DESC ")
    List<Auction> findLatestProposedAuctions(@Param("username") String username, Pageable pageable);

    @Query("SELECT a from Auction a " +
            "JOIN User u on u.participantId = a.consumerParticipantId" +
            " WHERE u.username = :username " +
            "ORDER BY a.createdOn DESC ")
    List<Auction> findLatestWonAuctions(@Param("username") String username, Pageable pageable);


}
