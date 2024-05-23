package com.enershare.service.logs;

import com.enershare.model.logs.Logs;
import com.enershare.repository.logs.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogsService {

    private final LogsRepository logsRepository;

    public Logs createLog(Logs logs) {
        return logsRepository.save(logs);
    }

    public Optional<Logs> getLogById(Long id) {
        return logsRepository.findById(id);
    }

    public List<Logs> getAllLogsSortedByCreatedOn() {
        return logsRepository.findAllLogsOrderByCreatedOnDesc();
    }

    public Page<Logs> getIngressLogsByEmail(String email, int page, int size,String sort, String direction) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        return logsRepository.getIngressLogsByEmail(email, pageable);
    }

    public Page<Logs> getEgressLogsByEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return logsRepository.getEgressLogsByEmail(email, pageable);
    }

    public long countIngressLogsByEmail(String email) {
        return logsRepository.countIngressLogsByEmail(email);
    }

    public long countEgressLogsByEmail(String email) {
        return logsRepository.countEgressLogsByEmail(email);
    }

    public List<Logs> getLatestIngressLogs(String email, int count) {
        return logsRepository.findLatestIngressLogs(email, PageRequest.of(0, count));
    }

    public List<Logs> getLatestEgressLogs(String email, int count) {
        return logsRepository.findLatestEgressLogs(email, PageRequest.of(0, count));
    }

}
