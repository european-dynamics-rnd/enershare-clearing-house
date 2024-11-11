package com.enershare.mapper;

import com.enershare.dto.resource.ResourceDTO;
import com.enershare.model.resource.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ResourceMapper {


    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    public abstract Resource mapDTOToEntity(ResourceDTO resourceDTO);
    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    public abstract ResourceDTO mapEntityToDTO(Resource resource);

    @Named("ConvertListToString")
    protected String convertListToString(List<String> value) {
        return value != null ? String.join(",", value) : null; // Join with a comma or any preferred delimiter
    }

    @Named("ConvertStringToList")
    protected List<String> convertStringToList(String value) {
        return value != null ? List.of(value.split(",")) : List.of(); // Split by comma to create a list
    }


}
