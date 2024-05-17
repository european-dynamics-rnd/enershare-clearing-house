package com.enershare.dto.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSummaryDTO {
    private String dataLabel;
    private String dateRange;
    private int ingressLogCount;
    private int egressLogCount;

}
