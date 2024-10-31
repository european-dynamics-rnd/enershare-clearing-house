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
@RequestMapping("/resources/sale")
@RequiredArgsConstructor
public class SaleController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Void> createSaleResource(@RequestBody ResourceDTO resourceDTO) {
        resourceDTO.setType("sale"); // Ensure the type is set for sale
        resourceService.createResource(resourceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getSaleResources() {
        List<Resource> saleResources = resourceService.getResourcesByType("sale");
        return saleResources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(saleResources);
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
}
