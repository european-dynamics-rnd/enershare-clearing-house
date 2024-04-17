package com.enershare.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class LogsDTO {
    private String consumer;
    private String provider;
    private String senderAgent;
    private String recipientAgent;
    private String contractId;
    private RequestParametersDTO requestParameters;
    private Map<String, Object> contextParameters;
    private String mode;
    private String stage;
    private String requestId;
}
