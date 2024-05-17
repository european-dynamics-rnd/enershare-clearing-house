package com.enershare.repository.logs;

import com.enershare.dto.logs.LogSummaryDTO;

import java.util.List;

public interface CustomLogsRepository {
    List<LogSummaryDTO> getCustomLogSummary(String email);

    List<LogSummaryDTO> getCustomLogSummaryLastTenHours(String email);

}
