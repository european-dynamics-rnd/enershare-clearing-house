package com.enershare.mapper;

import com.enershare.dto.auction.AuctionDTO;
import com.enershare.mapper.common.StringListMapper;
import com.enershare.model.auction.Auction;
import org.mapstruct.*;

import java.time.Instant;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE ,
        uses= StringListMapper.class)
public abstract class AuctionMapper {

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "createdOn", expression = "java(java.time.Instant.now())")
    @Mapping(target = "endDate",source = "endDate", qualifiedByName = "longToInstant")
    public abstract Auction mapAuctionDTOToEntity(AuctionDTO auctionDTO);

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "instantToLong")
    public abstract AuctionDTO mapEntityToDTO(Auction auction);

    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertListToString")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "longToInstant")
    public abstract void updateEntityFromDTO(AuctionDTO auctionDTO, @MappingTarget Auction auction);


    @Mapping(target = "artifactsIds", source = "artifactsIds", qualifiedByName = "ConvertStringToList")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "instantToLong")
    public abstract void updateDTOFromEntity(Auction auction, @MappingTarget AuctionDTO auctionDTO);


    @Named("longToInstant")
    Instant longToInstant(Long timestamp) {
        return timestamp != null ? Instant.ofEpochSecond(timestamp) : null;
    }

    @Named("instantToLong")
    Long instantToLong(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }

}
