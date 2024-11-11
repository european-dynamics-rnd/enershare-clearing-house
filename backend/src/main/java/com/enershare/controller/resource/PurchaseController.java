package com.enershare.controller.resource;

import com.enershare.dto.resource.ResourceDTO;
import com.enershare.model.resource.Resource;
import com.enershare.service.resource.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final ResourceService resourceService;
/*
    @PostMapping
    public ResponseEntity<Void> createPurchaseResource(@RequestBody ResourceDTO resourceDTO) {
        resourceDTO.setType("purchase"); // Ensure the type is set for purchase
        resourceService.createResource(resourceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getPurchaseResources() {
        List<Resource> purchaseResources = resourceService.getResourcesByStatus("purchase");
        return purchaseResources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(purchaseResources);
    }*/


}
