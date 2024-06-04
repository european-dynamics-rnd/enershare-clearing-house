package com.enershare.dto.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSummaryDTO {
    private String dataLabel;
    private LocalDateTime dateRange;
    private int ingressLogCount;
    private int egressLogCount;

}
