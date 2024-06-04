package com.enershare.controller.logs;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.logs.LogSummaryDTO;
import com.enershare.dto.logs.LogsDTO;
import com.enershare.filtering.SearchCriteria;
import com.enershare.mapper.LogsMapper;
import com.enershare.model.logs.Logs;
import com.enershare.repository.logs.LogsRepository;
import com.enershare.service.auth.JwtService;
import com.enershare.service.logs.LogsService;
import com.enershare.util.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogsController {

    private final LogsService logsService;
    private final LogsMapper logsMapper;
    private final JwtService jwtService;
    private final LogsRepository logsRepository;
    private final RequestUtils requestUtils;

    @PostMapping("/create")
    public ResponseEntity<Void> createLog(@RequestBody LogsDTO logsDTO) {
        Logs logs = logsMapper.mapConsumerDTOToEntity(logsDTO);
        logsService.createLog(logs);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logs> getLogs(@PathVariable Long id) {
        Optional<Logs> logsOptional = logsService.getLogById(id);
        return logsOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Logs>> getAllLogs() {
        List<Logs> logsList = logsService.getAllLogsSortedByCreatedOn();
        return logsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(logsList);
    }

    @PostMapping("/ingress")
    public ResponseEntity<Page<Logs>> getIngressLogs(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);

        Page<Logs> ingressLogs = logsService.getIngressLogsByEmail(email, searchRequestDTO);
        return ResponseEntity.ok(ingressLogs);
    }

    @GetMapping("/egress")
    public ResponseEntity<Page<Logs>> getEgressLogs(HttpServletRequest request,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "provider") String sort,
                                                    @RequestParam(defaultValue = "asc") String direction,
                                                    @RequestParam List<SearchCriteria> searchCriteriaList) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);
        Page<Logs> egressLogs = logsService.getEgressLogsByEmail(email, page, size, sort, direction, searchCriteriaList);
        return ResponseEntity.ok(egressLogs);
    }

    @GetMapping("/ingress/count")
    public ResponseEntity<Long> countIngressLogs(HttpServletRequest request) {
        String token = jwtService.getJwt(request);
        String email = jwtService.getUserIdByToken(token);
        //TODO: Once FE is finished pass the parameter here also
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        long count = logsService.countIngressLogsByEmail(email, searchCriteriaList);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/egress/count")
    public ResponseEntity<Long> countEgressLogs(HttpServletRequest request) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);

        //TODO: Replace this List with a parameter from FE
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        long count = logsService.countEgressLogsByEmail(email, searchCriteriaList);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/latestIngressLogs")
    public ResponseEntity<List<Logs>> getLatestIngressLogs(HttpServletRequest request,
                                                           @RequestParam(defaultValue = "5") int count) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);
        List<Logs> latestIngressLogs = logsService.getLatestIngressLogs(email, count);

        return ResponseEntity.ok(latestIngressLogs);
    }

    @GetMapping("/latestEgressLogs")
    public ResponseEntity<List<Logs>> getLatestEgressLogs(HttpServletRequest request,
                                                          @RequestParam(defaultValue = "5") int count) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);
        List<Logs> latestEgressLogs = logsService.getLatestEgressLogs(email, count);
        return ResponseEntity.ok(latestEgressLogs);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<LogSummaryDTO>> getSummary(HttpServletRequest request) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);
        List<LogSummaryDTO> summaries = logsRepository.getCustomLogSummary(email);
        return ResponseEntity.ok().body(summaries);
    }

    @GetMapping("/summaryHours")
    public ResponseEntity<List<LogSummaryDTO>> getLastTenHoursSummary(HttpServletRequest request) {
        String token = requestUtils.getTokenFromRequest(request);
        String email = requestUtils.getEmailFromToken(token);
        List<LogSummaryDTO> summaries = logsRepository.getCustomLogSummaryLastTenHours(email);
        return ResponseEntity.ok().body(summaries);
    }

}
