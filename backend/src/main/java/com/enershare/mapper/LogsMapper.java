package com.enershare.mapper;

import com.enershare.dto.logs.LogsDTO;
import com.enershare.model.logs.Logs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class LogsMapper {
    @Mapping(target = "purpose", source = "contextParameters.purpose")
    @Mapping(target = "datClaims", source = "contextParameters.datClaims")
    @Mapping(target = "resourceId", source = "requestParameters.resourceId")
    @Mapping(target = "resourceType", source = "requestParameters.resourceType")
    @Mapping(target = "action", source = "requestParameters.action")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    public abstract Logs mapDTOToEntity(LogsDTO logsDTO);

    public abstract LogsDTO mapEntityToDTO(Logs logs);
}