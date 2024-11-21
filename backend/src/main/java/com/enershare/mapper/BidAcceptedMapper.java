package com.enershare.mapper;

import com.enershare.dto.bid.BidAcceptedDTO;
import com.enershare.mapper.common.StringListMapper;
import com.enershare.model.bid.BidAccepted;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = StringListMapper.class)
public abstract class BidAcceptedMapper {

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    public abstract BidAccepted mapDTOToEntity(BidAcceptedDTO bidAcceptedDTO);

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    public abstract BidAcceptedDTO mapEntityToDTO(BidAccepted bidAccepted);
}
