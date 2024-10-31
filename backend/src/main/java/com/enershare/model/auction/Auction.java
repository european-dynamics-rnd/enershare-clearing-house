package com.enershare.model.auction;

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
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    @JsonProperty("resourceId")
    private String resourceId;

    @Column(name = "provider_connector_id")
    @JsonProperty("providerConnectorID")
    private String providerConnectorID;

    @Column(name = "provider_participant_id")
    @JsonProperty("providerParticipantIDSID")
    private String providerParticipantIDSID;

    @Column(name = "consumer_participant_id")
    @JsonProperty("consumerParticipantIDSID")
    private String consumerParticipantIDSID;

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

    @Column(name = "created_on", updatable = false) // Prevent updates after creation
    @CreationTimestamp // Use Hibernate's annotation for auto-generating the timestamp
    private Instant createdOn; // This field will hold the creation timestamp
}
