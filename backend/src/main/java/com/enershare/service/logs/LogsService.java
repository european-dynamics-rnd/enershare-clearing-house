package com.enershare.service.logs;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.logs.LogsDTO;
import com.enershare.dto.logs.RequestParametersDTO;
import com.enershare.filtering.specification.log.LogsSpecification;
import com.enershare.mapper.LogsMapper;
import com.enershare.model.logs.Logs;
import com.enershare.model.purchase.Purchase;
import com.enershare.model.resource.Resource;
import com.enershare.repository.logs.LogsRepository;
import com.enershare.repository.purchase.PurchaseRepository;
import com.enershare.repository.resource.ResourceRepository;
import com.enershare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogsService {

    private final LogsRepository logsRepository;
    private final LogsMapper logsMapper;
    private  final PurchaseRepository purchaseRepository;
    private final ResourceRepository resourceRepository;
    private final UserService userService;

    public ResponseEntity<String> createLog(LogsDTO logsDTO) {
        return Optional.ofNullable(logsDTO)
                .map(LogsDTO::getRequestParameters)
                .map(RequestParametersDTO::getResourceId)
                .map(resourceId -> {
                    List<Purchase> purchases = purchaseRepository.findPurchaseByResourceId(resourceId);
                    if (!purchases.isEmpty()) {
                        List<String> consumerParticipantsIds = purchases.stream().map(Purchase::getConsumerParticipantId).toList();
                        List<String> connectors = consumerParticipantsIds.stream()
                                .map(userService::getAvailableConnectors)
                                .flatMap(List::stream)
                                .toList();

                        boolean connectorPurchasedResourceFound = connectors.stream()
                                .anyMatch(c -> c.equals(logsDTO.getConsumer()));

                        Logs logs = logsMapper.mapDTOToEntity(logsDTO);
                        if (connectorPurchasedResourceFound) {
                            logs.setTransactionStatus("Transaction is completed");
                            logsRepository.save(logs);
                            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction is completed");
                        } else {
                            logs.setTransactionStatus("The resource is not purchased yet. Transaction is failed");
                            logsRepository.save(logs);
                            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                    .body("Forbidden: You need first to purchase this resource ");
                        }

                    } else {
                        Optional<Resource> resource = resourceRepository.findById(resourceId);

                        if (resource.isPresent()) {
                            Resource r = resource.get();
                            Logs logs = logsMapper.mapDTOToEntity(logsDTO);
                            if (r.getFree()) {
                                logs.setTransactionStatus("Transaction is completed");
                                logsRepository.save(logs);
                                return ResponseEntity.status(HttpStatus.CREATED).body("Transaction is completed");
                            } else {
                                logs.setTransactionStatus("The resource is not purchased yet. Transaction is failed");
                                logsRepository.save(logs);
                                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body("Forbidden: You need first to purchase this resource ");
                            }
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body("Bad Request: Resource does not exist");
                        }
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Bad Request: resourceId is missing"));
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
