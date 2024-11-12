package com.enershare.mapper.common;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public class StringListMapper {

    @Named("ConvertListToString")
    public String convertListToString(List<String> value) {
        return value != null ? String.join(",", value) : null; // Join with a comma or any preferred delimiter
    }

    @Named("ConvertStringToList")
    public List<String> convertStringToList(String value) {
        return value != null ? List.of(value.split(",")) : List.of(); // Split by comma to create a list
    }

}
