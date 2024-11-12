package com.enershare.controller.purchase;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.purchase.PurchaseDTO;
import com.enershare.model.purchase.Purchase;
import com.enershare.service.purchase.PurchaseService;
import com.enershare.service.user.UserEmailService;
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
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final UserEmailService userEmailService;

    @PostMapping
    public ResponseEntity<Void> createPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO) {
        purchaseService.createPurchase(purchaseDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
        Optional<Purchase> purchaseOptional = purchaseService.getPurchaseById(id);
        return purchaseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> resources = purchaseService.getAllPurchasesSortedByCreatedOn();
        return resources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(resources);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<Purchase>> getPurchasesByUser(HttpServletRequest request) {
        String email = userEmailService.getEmailFromRequest(request);
        List<Purchase> purchases = purchaseService.getPurchasesByUserEmail(email);
        return purchases.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(purchases);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Purchase>> getPurchasesByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String email = userEmailService.getEmailFromRequest(request);
        Page<Purchase> purchases = purchaseService.getPurchasesByCriteria(email,searchRequestDTO);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Purchase>> getLatestPurchases(HttpServletRequest request,@RequestParam(defaultValue = "5") int count) {
        String email = userEmailService.getEmailFromRequest(request);
        List<Purchase> latestResources = purchaseService.getLatestPurchases(email,count);
        return ResponseEntity.ok(latestResources);
    }




}
