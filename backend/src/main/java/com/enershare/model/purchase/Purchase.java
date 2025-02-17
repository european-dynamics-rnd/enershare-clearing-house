package com.enershare.model.purchase;

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
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "resource_id", nullable = false)
    private String resourceId;

    @Column(name = "artifacts_ids")
    private String artifactsIds;

    @Column(name="consumer_participant_id")
    private String consumerParticipantId ;

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
