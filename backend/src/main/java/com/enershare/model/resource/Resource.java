package com.enershare.model.resource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "resources")
public class Resource {

    @Id
    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(name = "artifacts_ids")
    private String artifactsIds;

    @Column(name = "provider_connector_id")
    private String providerConnectorId;

    @Column(name = "provider_participant_id")
    private String providerParticipantId;

    @Column(name = "free")
    private Boolean free;

    @Column(name = "hash")
    private String hash;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private Instant createdOn;


}