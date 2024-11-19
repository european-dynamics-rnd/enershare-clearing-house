package com.enershare.controller.logs;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.logs.LogSummaryDTO;
import com.enershare.dto.logs.LogsDTO;
import com.enershare.model.logs.Logs;
import com.enershare.repository.logs.LogsRepository;
import com.enershare.service.logs.LogsService;
import com.enershare.service.user.UsernameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogsController {

    private final LogsService logsService;
    private final UsernameService usernameService;
    private final LogsRepository logsRepository;

    @PostMapping("/create")
    public ResponseEntity<Void> createLog(@RequestBody LogsDTO logsDTO) {
        logsService.createLog(logsDTO);
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
        String username = usernameService.getUsernameFromRequest(request);
        Page<Logs> ingressLogs = logsService.getIngressLogsByUsername(username, searchRequestDTO);
        return ResponseEntity.ok(ingressLogs);
    }

    @PostMapping("/egress")
    public ResponseEntity<Page<Logs>> getEgressLogs(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String username = usernameService.getUsernameFromRequest(request);
        Page<Logs> egressLogs = logsService.getEgressLogsByUsername(username, searchRequestDTO);
        return ResponseEntity.ok(egressLogs);
    }

    @GetMapping("/latestIngressLogs")
    public ResponseEntity<List<Logs>> getLatestIngressLogs(HttpServletRequest request,
                                                           @RequestParam(defaultValue = "5") int count) {
        String username = usernameService.getUsernameFromRequest(request);
        List<Logs> latestIngressLogs = logsService.getLatestIngressLogs(username, count);
        return ResponseEntity.ok(latestIngressLogs);
    }

    @GetMapping("/latestEgressLogs")
    public ResponseEntity<List<Logs>> getLatestEgressLogs(HttpServletRequest request,
                                                          @RequestParam(defaultValue = "5") int count) {
        String username = usernameService.getUsernameFromRequest(request);
        List<Logs> latestEgressLogs = logsService.getLatestEgressLogs(username, count);
        return ResponseEntity.ok(latestEgressLogs);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<LogSummaryDTO>> getSummary(HttpServletRequest request) {
        String username = usernameService.getUsernameFromRequest(request);
        List<LogSummaryDTO> summaries = logsRepository.getCustomLogSummary(username);
        return ResponseEntity.ok().body(summaries);
    }

    @GetMapping("/summaryHours")
    public ResponseEntity<List<LogSummaryDTO>> getLastTenHoursSummary(HttpServletRequest request) {
        String username = usernameService.getUsernameFromRequest(request);
        List<LogSummaryDTO> summaries = logsRepository.getCustomLogSummaryLastTenHours(username);
        return ResponseEntity.ok().body(summaries);
    }

}
