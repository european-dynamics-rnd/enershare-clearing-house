package com.enershare.service.logs;

import com.enershare.model.logs.Logs;
import com.enershare.repository.logs.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogsService {

    private final LogsRepository logsRepository;

    @Autowired
    public LogsService(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    public Logs createLog(Logs logs) {
        return logsRepository.save(logs);
    }

    public Optional<Logs> getLogById(Long id) {
        return logsRepository.findById(id);
    }

    public List<Logs> getAllLogs() {
        return logsRepository.findAll();
    }

    public List<Logs> getAllLogsSortedByCreatedOn() {
        return logsRepository.findAllLogsOrderByCreatedOnDesc();
    }
}
