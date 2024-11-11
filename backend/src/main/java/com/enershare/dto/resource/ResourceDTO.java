package com.enershare.dto.resource;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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

    @JsonProperty("resourceID")
    @JsonAlias("resourceId")
    @NotBlank(message = "Resource Id is required")
    private String resourceId;

    private List<String> artifactsIds;

    @JsonProperty("providerConnectorID")
    @JsonAlias("providerConnectorId")
    @NotBlank(message = "Provider Connector Id is required")
    private String providerConnectorId ;

    @JsonProperty("providerParticipantIDSID")
    @JsonAlias("providerParticipantId")
    private String providerParticipantId ;

    private Boolean free;

    private String hash;

    private String status;

    @PositiveOrZero(message = "Price should be positive or zero ")
    private Double price;
}