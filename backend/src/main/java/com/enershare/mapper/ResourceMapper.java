package com.enershare.mapper;

import com.enershare.dto.resource.ResourceDTO;
import com.enershare.model.resource.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ResourceMapper {

    @Mapping(target = "resourceId", source = "resourceID")
    @Mapping(target = "providerConnectorID", source = "providerConnectorID")
    @Mapping(target = "providerParticipantIDSID", source = "providerParticipantIDSID")
    @Mapping(target = "artifactsIds", source = "artifactsIds")
    @Mapping(target = "free", source = "free")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "type", source = "type")
    public abstract Resource mapResourceDTOToEntity(ResourceDTO resourceDTO);

    public abstract ResourceDTO mapEntityToDTO(Resource resource);

    // Convert List<String> to String (comma-separated or any other delimiter)
    protected String mapArtifactsIds(List<String> value) {
        return value != null ? String.join(",", value) : null; // Join with a comma or any preferred delimiter
    }

    // Convert String to List<String>
    protected List<String> mapStringToList(String value) {
        return value != null ? List.of(value.split(",")) : List.of(); // Split by comma to create a list
    }

    // Convert Optional<String> to String
    protected String mapOptionalString(Optional<String> value) {
        return value.orElse(null); // Return null if not present
    }

    // Convert String to Optional<String>
    protected Optional<String> mapString(String value) {
        return Optional.ofNullable(value); // Wrap String in Optional
    }

    // Convert Optional<Boolean> to boolean
    protected boolean mapOptionalBoolean(Optional<Boolean> value) {
        return value.orElse(false); // Return false if not present
    }

    // Convert boolean to Optional<Boolean>
    protected Optional<Boolean> map(boolean value) {
        return Optional.of(value); // Wrap boolean in Optional
    }

    // Convert Optional<Integer> to Integer
    protected Integer mapOptionalInteger(Optional<Integer> value) {
        return value.orElse(null); // Return null if not present
    }

    // Convert Integer to Optional<Integer>
    protected Optional<Integer> map(Integer value) {
        return Optional.ofNullable(value); // Wrap Integer in Optional
    }
}
