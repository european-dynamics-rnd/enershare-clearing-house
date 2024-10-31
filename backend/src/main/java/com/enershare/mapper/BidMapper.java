package com.enershare.mapper;

import com.enershare.dto.bid.BidDTO;
import com.enershare.model.bid.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class BidMapper {

    public static final BidMapper INSTANCE = Mappers.getMapper(BidMapper.class);

    @Mapping(target = "resourceId", source = "resourceID") // Map resourceID to resourceId
    @Mapping(target = "consumerParticipantIDSID", source = "consumerParticipantIDSID") // Map consumerParticipantIDSID
    @Mapping(target = "artifactsIds", source = "artifactsIds")
    @Mapping(target = "auctionHash", source = "auctionHash") // Add auctionHash mapping
    @Mapping(target = "hash", source = "hash")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "free", source = "free") // Add free mapping
    public abstract Bid mapBidDTOToEntity(BidDTO bidDTO);

    public abstract BidDTO mapEntityToDTO(Bid bid);

    protected String mapOptionalString(Optional<String> value) {
        return value.orElse(null); // Return null if not present
    }

    // Convert String to Optional<String>
    protected Optional<String> mapStringToOptional(String value) {
        return Optional.ofNullable(value); // Wrap String in Optional
    }

    // Convert Optional<Boolean> to Boolean
    protected Boolean mapOptionalBoolean(Optional<Boolean> value) {
        return value.orElse(null); // Return null if not present for Boolean
    }

    // Convert boolean to Optional<Boolean>
    protected Optional<Boolean> mapBooleanToOptional(boolean value) {
        return Optional.of(value); // Wrap boolean in Optional
    }

    // Convert List<String> to String (comma-separated or any other delimiter)
    protected String mapArtifactsIdsToString(List<String> artifactsIds) {
        return artifactsIds != null ? String.join(",", artifactsIds) : null;
    }

    // Convert String (comma-separated) to List<String>
    protected List<String> mapStringToArtifactsIds(String artifactsIds) {
        return artifactsIds != null ? List.of(artifactsIds.split(",")) : List.of();
    }
}

