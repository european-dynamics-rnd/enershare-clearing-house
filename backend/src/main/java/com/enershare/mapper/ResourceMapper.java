package com.enershare.mapper;

import com.enershare.dto.resource.ResourceDTO;
import com.enershare.mapper.common.StringListMapper;
import com.enershare.model.resource.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses= StringListMapper.class)
public abstract class ResourceMapper {


    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    public abstract Resource mapDTOToEntity(ResourceDTO resourceDTO);
    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    public abstract ResourceDTO mapEntityToDTO(Resource resource);

}
