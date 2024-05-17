package com.enershare.dto.logs;

import lombok.Data;

@Data
public class LogSummaryDTO {
    private String dataLabel;
    private String dateRange;
    private int ingressLogCount;
    private int egressLogCount;
    
    public LogSummaryDTO(String dataLabel, String dateRange, int ingressLogCount, int egressLogCount) {
        this.dataLabel = dataLabel;
        this.dateRange = dateRange;
        this.ingressLogCount = ingressLogCount;
        this.egressLogCount = egressLogCount;
    }
}
