package com.enershare.model.auction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(name = "provider_connector_id")
    private String providerConnectorId;

    @Column(name = "provider_participant_id")
    private String providerParticipantId;

    @Column(name = "consumer_participant_id")
    private String consumerParticipantId;

    @Column(name = "artifacts_ids")
    private String artifactsIds;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "bids")
    private Integer bids;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "winner_bid_hash")
    private String winnerBidHash;

    @Column(name = "free")
    private Boolean free;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private Instant createdOn;
}
