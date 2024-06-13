package com.enershare.dto.logs;

import lombok.Data;

@Data
public class RequestParametersDTO {

    private String resourceId;
    private String resourceType;
    private String action;

}
