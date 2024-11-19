package com.enershare.service.logs;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.logs.LogsDTO;
import com.enershare.filtering.specification.log.LogsSpecification;
import com.enershare.mapper.LogsMapper;
import com.enershare.model.logs.Logs;
import com.enershare.repository.logs.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogsService {

    private final LogsRepository logsRepository;
    private final LogsMapper logsMapper;
    public void createLog(LogsDTO logsDTO) {
        Logs logs = logsMapper.mapDTOToEntity(logsDTO);
        logsRepository.save(logs);

    }

    public Optional<Logs> getLogById(Long id) {
        return logsRepository.findById(id);
    }

    public List<Logs> getAllLogsSortedByCreatedOn() {
        return logsRepository.findAllLogsOrderByCreatedOnDesc();
    }

    public Page<Logs> getIngressLogsByUsername(String username, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Logs> spec = LogsSpecification.ingressLogsByUsername(username, searchRequestDTO.getSearchCriteriaList());
        return logsRepository.findAll(spec, pageable);
    }

    public Page<Logs> getEgressLogsByUsername(String username, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Logs> spec = LogsSpecification.egressLogsByUsername(username, searchRequestDTO.getSearchCriteriaList());
        return logsRepository.findAll(spec, pageable);
    }

    public List<Logs> getLatestIngressLogs(String username, int count) {
        return logsRepository.findLatestIngressLogs(username, PageRequest.of(0, count));
    }

    public List<Logs> getLatestEgressLogs(String username, int count) {
        return logsRepository.findLatestEgressLogs(username, PageRequest.of(0, count));
    }



}
