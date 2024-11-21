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
@Table(name = "bid_accepted") // Updated table name to match new structure
public class BidAccepted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(name = "consumer_participant_id", nullable = false)
    private String consumerParticipantId;

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
