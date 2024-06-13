package com.enershare.dto.logs;

import lombok.Data;

@Data
public class LogsDTO {
    private String consumer;
    private String provider;
    private String senderAgent;
    private String recipientAgent;
    private String contractId;
    private RequestParametersDTO requestParameters;
    private ContextParametersDTO contextParameters;
    private String mode;
    private String stage;
    private String requestId;
}
