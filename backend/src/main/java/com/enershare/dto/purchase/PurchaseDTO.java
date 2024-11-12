package com.enershare.dto.purchase;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {

    @JsonProperty("resourceID")
    @JsonAlias("resourceId")
    @NotBlank(message = "Resource Id is required")
    private String resourceId;

    private List<String> artifactsIds;

    @JsonProperty("consumerParticipantIDSID")
    @JsonAlias("consumerParticipantId")
    @NotBlank(message = "Consumer Participant Id is required")
    private String consumerParticipantId ;

    private String hash;

    private String status;

    @PositiveOrZero(message = "Price should be positive or zero ")
    private Double price;

}
