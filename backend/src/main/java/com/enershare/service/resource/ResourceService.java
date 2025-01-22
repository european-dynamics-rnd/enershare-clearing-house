package com.enershare.service.resource;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.resource.ResourceDTO;
import com.enershare.filtering.specification.resource.ResourceSpecification;
import com.enershare.mapper.ResourceMapper;
import com.enershare.model.resource.Resource;
import com.enershare.repository.resource.ResourceRepository;
import com.enershare.service.auth.BasicAuthenticationService;
import com.enershare.utils.RunnableWithReturn;
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
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final BasicAuthenticationService basicAuthenticationService;

    public ResponseEntity<Void> createResource(ResourceDTO resourceDTO) {
        Resource resource = resourceMapper.mapDTOToEntity(resourceDTO);
        resourceRepository.save(resource);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Optional<Resource> getResourceById(String id) {
        return resourceRepository.findById(id);
    }

    public Page<Resource> getResourcesByCriteria(String username, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Resource> spec = ResourceSpecification.resourcesByUsername(username, searchRequestDTO.getSearchCriteriaList());
        return resourceRepository.findAll(spec, pageable);
    }

    public List<Resource> getLatestResources(String username, int count) {
        return resourceRepository.findLatestResources(username, PageRequest.of(0, count));
    }

    public List<Resource> getAllResourcesSortedByCreatedOn() {
        return resourceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Resource> getResourcesByUsername(String username) {
        return resourceRepository.findResourcesByUsername(username);
    }

    public List<Resource> getResourcesByStatus(String status) {
        return resourceRepository.findByStatus(status);
    }

    public ResponseEntity<Void> createResourceWithBasicAuthentication(String authHeader, RunnableWithReturn<ResponseEntity<Void>> createResourceAction) {
        return basicAuthenticationService.authenticateAndExecute(authHeader, createResourceAction);
    }
}
