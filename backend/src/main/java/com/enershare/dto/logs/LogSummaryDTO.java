package com.enershare.dto.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSummaryDTO {
    private String dataLabel;
    private Instant dateRange;
    private int ingressLogCount;
    private int egressLogCount;

}
