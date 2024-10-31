package com.enershare.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author vtopa
 * @created 30/10/2024 - 13:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {

    private String resourceID;
    private Boolean free;
    private List<String> artifactsIds;
    private String auctionHash;
    private String hash;
    private String status;
    private Instant createdOn;

    // Optional properties specific to each JSON type
    private Optional<String> consumerParticipantIDSID = Optional.empty();
    private Optional<String> providerConnectorID = Optional.empty();
    private Optional<String> providerParticipantIDSID = Optional.empty();

}
