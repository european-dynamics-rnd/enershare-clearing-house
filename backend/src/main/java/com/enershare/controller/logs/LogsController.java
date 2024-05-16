package com.enershare.controller.logs;

import com.enershare.dto.request.LogsDTO;
import com.enershare.mapper.LogsMapper;
import com.enershare.model.logs.Logs;
import com.enershare.service.auth.JwtService;
import com.enershare.service.logs.LogsService;
import com.enershare.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;
    @Autowired
    private LogsMapper logsMapper;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createLog(@RequestBody LogsDTO logsDTO) {
        Logs logs = logsMapper.mapConsumerDTOToEntity(logsDTO);
        logsService.createLog(logs);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLogs(@PathVariable Long id) {
        Optional<Logs> logsOptional = logsService.getLogById(id);
        if (logsOptional.isPresent()) {
            return ResponseEntity.ok(logsOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Logs>> getAllLogs() {
        List<Logs> logsList = logsService.getAllLogsSortedByCreatedOn();
        if (!logsList.isEmpty()) {
            return ResponseEntity.ok(logsList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ingress")
    public ResponseEntity<Page<Logs>> getIngressLogs(HttpServletRequest request,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        String token = jwtService.getJwt(request);
        String email = jwtService.getUserIdByToken(token);
        Page<Logs> ingressLogs = logsService.getIngressLogsByEmail(email, page, size);
        return ResponseEntity.ok(ingressLogs);
    }

    @GetMapping("/egress")
    public ResponseEntity<Page<Logs>> getEgressLogs(HttpServletRequest request,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        String token = jwtService.getJwt(request);
        String email = jwtService.getUserIdByToken(token);
        Page<Logs> egressLogs = logsService.getEgressLogsByEmail(email, page, size);
        return ResponseEntity.ok(egressLogs);
    }

    @GetMapping("/ingress/count")
    public ResponseEntity<Long> countIngressLogs(HttpServletRequest request) {
        String token = jwtService.getJwt(request);
        String email = jwtService.getUserIdByToken(token);
        long count = logsService.countIngressLogsByEmail(email);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/egress/count")
    public ResponseEntity<Long> countEgressLogs(HttpServletRequest request) {
        String token = jwtService.getJwt(request);
        String email = jwtService.getUserIdByToken(token);
        long count = logsService.countEgressLogsByEmail(email);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/latestIngressLogs")
    public ResponseEntity<List<Logs>> getLatestIngressLogs(@RequestParam(defaultValue = "5") int count) {
        List<Logs> latestIngressLogs = logsService.getLatestIngressLogs(count);
        return ResponseEntity.ok(latestIngressLogs);
    }

    @GetMapping("/latestEgressLogs")
    public ResponseEntity<List<Logs>> getLatestEgressLogs(@RequestParam(defaultValue = "5") int count) {
        List<Logs> latestEgressLogs = logsService.getLatestEgressLogs(count);
        return ResponseEntity.ok(latestEgressLogs);
    }

}
