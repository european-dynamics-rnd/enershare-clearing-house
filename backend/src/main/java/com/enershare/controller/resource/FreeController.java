package com.enershare.controller.resource;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.resource.ResourceDTO;
import com.enershare.model.resource.Resource;
import com.enershare.service.resource.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @PostMapping("/create")
    public ResponseEntity<Void> createFreeResource(@Valid @RequestBody ResourceDTO resourceDTO,
                                                   @RequestHeader(value = "Authorization", required = false) String authHeader){
        resourceDTO.setStatus("free");
        return resourceService.createResourceWithBasicAuthentication(authHeader,()->resourceService.createResource(resourceDTO));

    }


    @GetMapping
    public ResponseEntity<List<Resource>> getFreeResources() {
        List<Resource> freeResources = resourceService.getResourcesByStatus("free");
        return freeResources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(freeResources);
    }

}
