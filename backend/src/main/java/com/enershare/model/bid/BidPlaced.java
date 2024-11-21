package com.enershare.model.bid;

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
@Table(name = "bid_placed")
public class BidPlaced {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(name = "provider_connector_id", nullable = false)
    private String providerConnectorId;

    @Column(name = "provider_participant_id", nullable = false)
    private String providerParticipantId;

    @Column(name = "free", nullable = false)
    private boolean free;

    @Column(name = "artifacts_ids", nullable = true)
    private String artifactsIds;

    @Column(name = "auction_hash", nullable = false)
    private String auctionHash;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private Instant createdOn; // Auto-generated creation timestamp
}
