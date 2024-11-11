package com.enershare.controller.resource;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.model.resource.Resource;
import com.enershare.service.resource.ResourceService;
import com.enershare.service.user.UserEmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;
    private  final UserEmailService userEmailService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResource(@PathVariable Long id) {
        Optional<Resource> resourceOptional = resourceService.getResourceById(id);
        return resourceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.getAllResourcesSortedByCreatedOn();
        return resources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resources);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Resource>> getResourcesByUser(HttpServletRequest request) {
        String email = userEmailService.getEmailFromRequest(request);
        List<Resource> resources = resourceService.getResourcesByUserEmail(email);
        return resources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resources);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Resource>> getResourcesByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String email = userEmailService.getEmailFromRequest(request);
        Page<Resource> resources = resourceService.getResourcesByCriteria(email,searchRequestDTO);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Resource>> getLatestResources(HttpServletRequest request,@RequestParam(defaultValue = "5") int count) {
        String email = userEmailService.getEmailFromRequest(request);
        List<Resource> latestResources = resourceService.getLatestResources(email,count);
        return ResponseEntity.ok(latestResources);
    }

}
