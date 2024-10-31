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
        Resource resource = resourceMapper.mapResourceDTOToEntity(resourceDTO);
        resourceRepository.save(resource);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Page<Resource> getResourcesByCriteria(SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Resource> spec = ResourceSpecification.filterResources(searchRequestDTO.getSearchCriteriaList());
        return resourceRepository.findAll(spec, pageable);
    }

    public List<Resource> getLatestResources(int count) {
        Pageable pageable = PageRequest.of(0, count);
        return resourceRepository.findLatestResources(pageable);
    }

    public List<Resource> getAllResourcesSortedByCreatedOn() {
        return resourceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Resource> getResourcesByType(String type) {
        return resourceRepository.findByType(type);
    }
}
