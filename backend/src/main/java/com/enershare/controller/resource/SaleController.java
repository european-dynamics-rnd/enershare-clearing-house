package com.enershare.controller.resource;

import com.enershare.dto.resource.ResourceDTO;
import com.enershare.model.resource.Resource;
import com.enershare.service.resource.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources/sale")
@RequiredArgsConstructor
public class SaleController {

    private final ResourceService resourceService;

    @PostMapping("/create")
    public ResponseEntity<Void> createSaleResource(@Valid @RequestBody ResourceDTO resourceDTO) {
        resourceDTO.setStatus("onSale");
        resourceService.createResource(resourceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getSaleResources() {
        List<Resource> saleResources = resourceService.getResourcesByStatus("onSale");
        return saleResources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(saleResources);
    }


}
