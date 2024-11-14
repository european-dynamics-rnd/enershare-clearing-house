package com.enershare.dto.auction;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author vtopa
 * @created 30/10/2024 - 13:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {

    @JsonProperty("resourceID")
    @JsonAlias("resourceId")
    @NotBlank(message = "Resource Id is required")
    private String resourceId;

    private List<String> artifactsIds;

    @NotBlank(message = "Hash is required")
    private String hash;

    private String status;

    @JsonProperty("consumerParticipantIDSID")
    @JsonAlias("consumerParticipantId")
    private String consumerParticipantId;

    @JsonProperty("providerConnectorID")
    @JsonAlias("providerConnectorId")
    private String providerConnectorId;

    @JsonProperty("providerParticipantIDSID")
    @JsonAlias("providerParticipantId")
    private String providerParticipantId;

    private String winnerBidHash;

    private Boolean free;

    private Integer bids;

    private Long endDate;
}
