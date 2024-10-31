package com.enershare.controller.resource;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.resource.ResourceDTO;
import com.enershare.model.resource.Resource;
import com.enershare.service.resource.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources/free")
@RequiredArgsConstructor
public class FreeController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Void> createFreeResource(@RequestBody ResourceDTO resourceDTO) {
        resourceDTO.setType("free"); // Ensure the type is set for free
        resourceService.createResource(resourceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getFreeResources() {
        List<Resource> freeResources = resourceService.getResourcesByType("free");
        return freeResources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(freeResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResource(@PathVariable Long id) {
        Optional<Resource> resourceOptional = resourceService.getResourceById(id);
        return resourceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Resource>> getResourcesByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        Page<Resource> resources = resourceService.getResourcesByCriteria(searchRequestDTO);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Resource>> getLatestResources(@RequestParam(defaultValue = "5") int count) {
        List<Resource> latestResources = resourceService.getLatestResources(count);
        return ResponseEntity.ok(latestResources);
    }
}
