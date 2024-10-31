package com.enershare.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {
    private String resourceID;
    private List<String> artifactsIds;
    private Instant createdOn;
    private String type;
    private Optional<String> consumerParticipantIDSID = Optional.empty();
    private Optional<String> providerConnectorID = Optional.empty();
    private Optional<String> providerParticipantIDSID = Optional.empty();
    private Optional<Boolean> free = Optional.empty();
    private Optional<String> hash = Optional.empty();
    private Optional<String> status = Optional.empty();
    private Optional<Integer> price = Optional.empty();
}