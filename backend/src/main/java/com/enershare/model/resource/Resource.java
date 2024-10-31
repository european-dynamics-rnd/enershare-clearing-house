package com.enershare.model.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id", nullable = false)
    @JsonProperty("resourceID")
    private String resourceId;

    @Column(name = "artifacts_ids")
    private String artifactsIds;

    @Column(name = "consumer_participant_id", nullable = true)
    @JsonProperty("consumerParticipantIDSID")
    private String consumerParticipantIDSID;

    @Column(name = "provider_connector_id", nullable = true)
    @JsonProperty("providerConnectorID")
    private String providerConnectorID;

    @Column(name = "provider_participant_id", nullable = true)
    @JsonProperty("providerParticipantIDSID")
    private String providerParticipantIDSID;

    @Column(name = "free", nullable = true)
    private Boolean free;

    @Column(name = "hash", nullable = true)
    private String hash;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "price", nullable = true)
    private Integer price;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private Instant createdOn;

    @Column(name = "type", nullable = true)
    private String type;

    public List<String> getArtifactsIds() {
        return artifactsIds != null ? List.of(artifactsIds.split(",")) : List.of();
    }

    public void setArtifactsIds(List<String> artifactsIds) {
        this.artifactsIds = String.join(",", artifactsIds); // Convert List to String
    }
}