package com.enershare.mapper;

import com.enershare.dto.bid.BidPlacedDTO;
import com.enershare.mapper.common.StringListMapper;
import com.enershare.model.bid.BidPlaced;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = StringListMapper.class)
public abstract class BidPlacedMapper {

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    public abstract BidPlaced mapDTOToEntity(BidPlacedDTO bidPlacedDTO);

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    public abstract BidPlacedDTO mapEntityToDTO(BidPlaced bidPlaced);
}
