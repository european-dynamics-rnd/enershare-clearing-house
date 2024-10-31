package com.enershare.model.bid;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    @JsonProperty("resourceId")
    private String resourceId;

    @Column(name = "consumer_participant_id")
    @JsonProperty("consumerParticipantIDSID")
    private String consumerParticipantIDSID;

    @Column(name = "free", nullable = false)
    private boolean free;

    @Column(name = "artifacts_ids")
    private String artifactsIds;

    @Column(name = "auction_hash")
    private String auctionHash;

    @Column(name = "hash", nullable = false)
    private String hash;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_on", updatable = false) // Prevent updates after creation
    @CreationTimestamp // Use Hibernate's annotation for auto-generating the timestamp
    private Instant createdOn; // This field will hold the creation timestamp
}
