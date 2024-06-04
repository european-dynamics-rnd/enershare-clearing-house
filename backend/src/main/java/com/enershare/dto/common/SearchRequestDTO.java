package com.enershare.dto.common;

import com.enershare.filtering.SearchCriteria;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDTO {

    private int page;
    private int pageSize;
    private String sort;
    private String direction;
    private List<SearchCriteria> searchCriteriaList;
}
