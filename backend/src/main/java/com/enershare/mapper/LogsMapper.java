package com.enershare.mapper;

import com.enershare.dto.logs.LogsDTO;
import com.enershare.model.logs.Logs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.util.Map;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LogsMapper {
    @Named("getPurposeFromContext")
    static String getPurposeFromContext(Map<String, Object> contextParameters) {
        return (String) contextParameters.get("purpose");
    }

    @Named("getDatClaimsFromContext")
    static String getDatClaimsFromContext(Map<String, Object> contextParameters) {
        return (String) contextParameters.get("datClaims");
    }

    @Mapping(target = "purpose", source = "contextParameters", qualifiedByName = "getPurposeFromContext")
    @Mapping(target = "datClaims", source = "contextParameters", qualifiedByName = "getDatClaimsFromContext")
    Logs mapConsumerDTOToEntity(LogsDTO logsDTO);

    LogsDTO mapEntityToDTO(Logs logs);
}

