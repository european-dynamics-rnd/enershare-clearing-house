package com.enershare.model.logs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
public class Logs {

    @Id
    @GeneratedValue
    private Long id;

    private String consumer;

    private String provider;

    @Column(name = "sender_agent")
    @JsonProperty("senderAgent")
    private String senderAgent;

    @Column(name = "recipient_agent")
    @JsonProperty("recipientAgent")
    private String recipientAgent;

    @Column(name = "contract_id")
    @JsonProperty("contractId")
    private String contractId;

    @Column(name = "resource_id")
    @JsonProperty("resourceId")
    private String resourceId;

    @Column(name = "resource_type")
    @JsonProperty("resourceType")
    private String resourceType;

    private String action;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "dat_claims")
    private String datClaims;

    private String mode;

    private String stage;

    private String connectorUrl;

    @Column(name = "request_id")
    @JsonProperty("requestId")
    private String requestId;
    @CurrentTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
