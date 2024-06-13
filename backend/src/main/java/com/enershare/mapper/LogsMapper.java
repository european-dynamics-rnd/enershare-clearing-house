package com.enershare.mapper;

import com.enershare.dto.logs.LogsDTO;
import com.enershare.model.logs.Logs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class LogsMapper {
    @Named("getPurposeFromContext")
    public String getPurposeFromContext(LogsDTO logsDTO) {
        if (logsDTO.getContextParameters() != null) {
            return (String) logsDTO.getContextParameters().get("purpose");
        }
        return null;
    }

    @Named("getDatClaimsFromContext")
    public String getDatClaimsFromContext(LogsDTO logsDTO) {
        if (logsDTO.getContextParameters() != null) {
            return (String) logsDTO.getContextParameters().get("datClaims");
        }
        return null;
    }

    @Named("getResourceIdFromRequestParameters")
    public String getResourceIdFromRequestParameters(LogsDTO logsDTO) {
        if (logsDTO.getRequestParameters() != null) {
            return (String) logsDTO.getRequestParameters().getResourceId();
        }
        return null;
    }

    @Named("getResourceTypeFromRequestParameters")
    public String getResourceTypeFromRequestParameters(LogsDTO logsDTO) {
        if (logsDTO.getRequestParameters() != null) {
            return (String) logsDTO.getRequestParameters().getResourceType();
        }
        return null;
    }

    @Named("getActionFromRequestParameters")
    public String getActionFromRequestParameters(LogsDTO logsDTO) {
        if (logsDTO.getRequestParameters() != null) {
            return (String) logsDTO.getRequestParameters().getAction();
        }
        return null;
    }

    @Mapping(target = "purpose", source = "logsDTO", qualifiedByName = "getPurposeFromContext")
    @Mapping(target = "datClaims", source = "logsDTO", qualifiedByName = "getDatClaimsFromContext")
    @Mapping(target = "resourceId", source = "logsDTO", qualifiedByName = "getResourceIdFromRequestParameters")
    @Mapping(target = "resourceType", source = "logsDTO", qualifiedByName = "getResourceTypeFromRequestParameters")
    @Mapping(target = "action", source = "logsDTO", qualifiedByName = "getActionFromRequestParameters")
    public abstract Logs mapConsumerDTOToEntity(LogsDTO logsDTO);

    public abstract LogsDTO mapEntityToDTO(Logs logs);
}