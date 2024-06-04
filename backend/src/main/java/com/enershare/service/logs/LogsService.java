package com.enershare.service.logs;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.filtering.SearchCriteria;
import com.enershare.filtering.specification.LogsSpecification;
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


    public Logs createLog(Logs logs) {
        return logsRepository.save(logs);
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

    public Page<Logs> getEgressLogsByEmail(String email, int page, int size, String sort, String direction, List<SearchCriteria> searchCriteriaList) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        //TODO: The Next  line of code is only for testing. The Search Criteria List should be fetched from front-end
//        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        Specification<Logs> spec = LogsSpecification.egressLogsByEmail(email, searchCriteriaList);
        return logsRepository.findAll(spec, pageable);

    }

    public long countIngressLogsByEmail(String email, List<SearchCriteria> searchCriteriaList) {

        //TODO: The Next 8 lines of code are only for testing. The Search Criteria List should be fetched from front-end
//        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
//        LocalDateTime specificDateTime = LocalDateTime.of(2024, 5, 30, 10, 15, 30);
//        SearchCriteria searchCriteria = new SearchCriteria("action", "RE");
//        SearchCriteria searchCriteria2 = new SearchCriteria("contractId", "contractId3");
//        SearchCriteria searchCriteria3 = new SearchCriteria("createdOn", specificDateTime);
//        searchCriteriaList.add(searchCriteria);
//        searchCriteriaList.add(searchCriteria2);
//        searchCriteriaList.add(searchCriteria3);


        Specification<Logs> spec = LogsSpecification.ingressLogsByEmail(email, searchCriteriaList);
        return logsRepository.count(spec);

    }

    public long countEgressLogsByEmail(String email, List<SearchCriteria> searchCriteriaList) {

        //TODO: The Next  line of code is only for testing. The Search Criteria List should be fetched from front-end


        Specification<Logs> spec = LogsSpecification.egressLogsByEmail(email, searchCriteriaList);
        return logsRepository.count(spec);
    }

    public List<Logs> getLatestIngressLogs(String email, int count) {
        return logsRepository.findLatestIngressLogs(email, PageRequest.of(0, count));
    }

    public List<Logs> getLatestEgressLogs(String email, int count) {
        return logsRepository.findLatestEgressLogs(email, PageRequest.of(0, count));
    }

}
