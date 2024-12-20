package com.enershare.model.logs;

import com.enershare.dto.logs.LogSummaryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
@SqlResultSetMapping(
        name = "LogSummaryDTO",
        classes = @ConstructorResult(
                targetClass = LogSummaryDTO.class,
                columns = {
                        @ColumnResult(name = "dataLabel", type = String.class),
                        @ColumnResult(name = "dateRange", type = String.class),
                        @ColumnResult(name = "ingressLogCount", type = Integer.class),
                        @ColumnResult(name = "egressLogCount", type = Integer.class)
                }
        )
)
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

    @Column(name = "request_id")
    @JsonProperty("requestId")
    private String requestId;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "transaction_status")
    private String transactionStatus;
}