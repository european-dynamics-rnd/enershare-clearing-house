package com.enershare.mapper;

import com.enershare.dto.purchase.PurchaseDTO;
import com.enershare.mapper.common.StringListMapper;
import com.enershare.model.purchase.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses= StringListMapper.class)
public abstract class PurchaseMapper {

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    public abstract Purchase mapDTOToEntity(PurchaseDTO purchaseDTO);
    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    public abstract PurchaseDTO mapEntityToDTO(Purchase purchase);


}
