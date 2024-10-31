package com.enershare.dto.auction;

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

    private String resourceID;
    private List<String> artifactsIds;
    private String hash;
    private String status;
    private Instant createdOn;
    // Optional fields for differences between JSON structures
    private Optional<String> consumerParticipantIDSID = Optional.empty();
    private Optional<String> providerConnectorID = Optional.empty();
    private Optional<String> providerParticipantIDSID = Optional.empty();
    private Optional<String> winnerBidHash = Optional.empty();
    private Optional<Boolean> free = Optional.empty();
    private Optional<Integer> bids = Optional.empty();
    private Optional<Long> endDate = Optional.empty();
}
