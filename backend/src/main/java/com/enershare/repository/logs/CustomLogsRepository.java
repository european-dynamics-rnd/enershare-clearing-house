package com.enershare.repository.logs;

import com.enershare.dto.logs.LogSummaryDTO;

import java.util.List;

public interface CustomLogsRepository {
    List<LogSummaryDTO> getCustomLogSummary(String username);

    List<LogSummaryDTO> getCustomLogSummaryLastTenHours(String username);

}
