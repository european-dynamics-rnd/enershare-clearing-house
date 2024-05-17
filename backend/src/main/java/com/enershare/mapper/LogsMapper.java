package com.enershare.mapper;

import com.enershare.dto.logs.LogsDTO;
import com.enershare.model.logs.Logs;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LogsMapper {
    public Logs mapConsumerDTOToEntity(LogsDTO logsDTO) {
        Logs logs = new Logs();
        logs.setConsumer(logsDTO.getConsumer());
        logs.setProvider(logsDTO.getProvider());
        logs.setSenderAgent(logsDTO.getSenderAgent());
        logs.setRecipientAgent(logsDTO.getRecipientAgent());
        logs.setContractId(logsDTO.getContractId());
        logs.setResourceId(logsDTO.getRequestParameters().getResourceId());
        logs.setResourceType(logsDTO.getRequestParameters().getResourceType());
        logs.setAction(logsDTO.getRequestParameters().getAction());

        // Accessing context parameters
        if (logsDTO.getContextParameters() != null) {
            Map<String, Object> contextParams = logsDTO.getContextParameters();
            logs.setPurpose((String) contextParams.get("purpose")); // Assuming purpose is a string
            logs.setDatClaims((String) contextParams.get("datClaims")); // Assuming datClaims is a string
        }

        logs.setMode(logsDTO.getMode());
        logs.setStage(logsDTO.getStage());
        logs.setRequestId(logsDTO.getRequestId());
        return logs;
    }
}

