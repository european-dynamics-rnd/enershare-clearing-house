package com.enershare.mapper;


import com.enershare.dto.auction.AuctionDTO;
import com.enershare.model.auction.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class AuctionMapper {

    @Mapping(target = "resourceId", source = "resourceID") // Ensure property names match
    @Mapping(target = "providerConnectorID", source = "providerConnectorID")
    @Mapping(target = "providerParticipantIDSID", source = "providerParticipantIDSID")
    @Mapping(target = "artifactsIds", source = "artifactsIds")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "hash", source = "hash")
    @Mapping(target = "endDate", source = "endDate")
    public abstract Auction mapAuctionDTOToEntity(AuctionDTO auctionDTO);

    public abstract AuctionDTO mapEntityToDTO(Auction auction);

    protected String mapOptionalString(Optional<String> value) {
        return value.orElse(null); // Return null if not present
    }

    // Map List<String> to String (comma-separated)
    protected String mapArtifactsIds(List<String> artifactsIds) {
        return artifactsIds != null ? String.join(",", artifactsIds) : null;
    }

    // Map String to List<String>
    protected List<String> mapStringToList(String value) {
        return value != null ? List.of(value.split(",")) : null;
    }

    // Map String to Optional<String>
    protected Optional<String> mapToOptionalString(String value) {
        return Optional.ofNullable(value); // Wrap String in Optional
    }

    // Map Optional<Long> to Instant
    protected Instant mapOptionalLongToInstant(Optional<Long> value) {
        return value.map(Instant::ofEpochMilli).orElse(null); // Convert Long to Instant or return null
    }

    // Map Instant to Optional<Long>
    protected Optional<Long> mapInstantToOptionalLong(Instant value) {
        return Optional.ofNullable(value).map(Instant::toEpochMilli); // Convert Instant to Long or return Optional.empty()
    }

    // Map Optional<Integer> to Integer
    protected Integer mapOptionalInteger(Optional<Integer> value) {
        return value.orElse(null); // Return null if not present
    }

    // Map Integer to Optional<Integer>
    protected Optional<Integer> mapToOptionalInteger(Integer value) {
        return Optional.ofNullable(value); // Wrap Integer in Optional
    }
}
