package com.enershare.controller.request;

import com.enershare.dto.request.LogsDTO;
import com.enershare.mapper.LogsMapper;
import com.enershare.model.logs.Logs;
import com.enershare.service.logs.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
