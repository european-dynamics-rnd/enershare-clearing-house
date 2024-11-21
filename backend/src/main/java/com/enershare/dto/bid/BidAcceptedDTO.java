package com.enershare.dto.bid;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidAcceptedDTO {

    @JsonProperty("resourceID")
    @JsonAlias("resourceId")
    @NotBlank(message = "Resource ID is required")
    private String resourceId;

    @JsonProperty("consumerParticipantIDSID")
    @JsonAlias("consumerParticipantId")
    @NotBlank(message = "Consumer Participant ID is required")
    private String consumerParticipantId;

    private List<String> artifactsIds;

    private boolean free;

    @JsonProperty("auctionHash")
    @NotBlank(message = "Auction Hash is required")
    private String auctionHash;

    @NotBlank(message = "Hash is required")
    private String hash;

    @NotBlank(message = "Status is required")
    private String status;
}
