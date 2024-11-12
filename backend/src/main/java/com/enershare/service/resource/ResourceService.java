package com.enershare.service.resource;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.resource.ResourceDTO;
import com.enershare.filtering.specification.resource.ResourceSpecification;
import com.enershare.mapper.ResourceMapper;
import com.enershare.model.resource.Resource;
import com.enershare.repository.resource.ResourceRepository;
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
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public void createResource(ResourceDTO resourceDTO) {
        Resource resource = resourceMapper.mapDTOToEntity(resourceDTO);
        resourceRepository.save(resource);
    }

    public Optional<Resource> getResourceById(String id) {
        return resourceRepository.findById(id);
    }

    public Page<Resource> getResourcesByCriteria(String email, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Resource> spec = ResourceSpecification.resourcesByEmail(email,searchRequestDTO.getSearchCriteriaList());
        return resourceRepository.findAll(spec, pageable);
    }

    public List<Resource> getLatestResources(String email,int count) {
        return resourceRepository.findLatestResources(email, PageRequest.of(0, count));
    }

    public List<Resource> getAllResourcesSortedByCreatedOn() {
        return resourceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Resource> getResourcesByUserEmail(String email){
        return resourceRepository.findResourcesByUserEmail(email);
    }

    public List<Resource> getResourcesByStatus(String status) {
        return resourceRepository.findByStatus(status);
    }
}
