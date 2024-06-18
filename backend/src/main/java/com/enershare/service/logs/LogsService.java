package com.enershare.service.logs;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.logs.LogsDTO;
import com.enershare.filtering.specification.LogsSpecification;
import com.enershare.mapper.LogsMapper;
import com.enershare.model.logs.Logs;
import com.enershare.repository.logs.LogsRepository;
import com.enershare.service.auth.AuthenticationService;
import com.enershare.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogsService {

    private final LogsRepository logsRepository;
    private final LogsMapper logsMapper;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    //Basic creation of log without authentication
    public void createLog(LogsDTO logsDTO) {
        Logs logs = logsMapper.mapConsumerDTOToEntity(logsDTO);
        logsRepository.save(logs);
    }

    public Optional<Logs> getLogById(Long id) {
        return logsRepository.findById(id);
    }

    public List<Logs> getAllLogsSortedByCreatedOn() {
        return logsRepository.findAllLogsOrderByCreatedOnDesc();
    }

    public Page<Logs> getIngressLogsByEmail(String email, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Logs> spec = LogsSpecification.ingressLogsByEmail(email, searchRequestDTO.getSearchCriteriaList());
        return logsRepository.findAll(spec, pageable);
    }

    public Page<Logs> getEgressLogsByEmail(String email, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Logs> spec = LogsSpecification.egressLogsByEmail(email, searchRequestDTO.getSearchCriteriaList());
        return logsRepository.findAll(spec, pageable);
    }

    public List<Logs> getLatestIngressLogs(String email, int count) {
        return logsRepository.findLatestIngressLogs(email, PageRequest.of(0, count));
    }

    public List<Logs> getLatestEgressLogs(String email, int count) {
        return logsRepository.findLatestEgressLogs(email, PageRequest.of(0, count));
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return jwtService.getJwt(request);
    }

    public String getEmailFromToken(String token) {
        return jwtService.getUserIdByToken(token);
    }

    // this function encapsulates getTokenFromRequest and getEmailFromToken
    public String getEmailFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        return getEmailFromToken(token);
    }

    public ResponseEntity<Void> createLogWithBasicAuthentication(String authHeader, Runnable createLogAction) {
        return authenticationService.authenticateAndExecute(authHeader, createLogAction);
    }

}
