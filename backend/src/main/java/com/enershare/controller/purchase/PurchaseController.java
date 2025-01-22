package com.enershare.controller.purchase;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.purchase.AmountDTO;
import com.enershare.dto.purchase.PurchaseDTO;
import com.enershare.model.purchase.Purchase;
import com.enershare.service.purchase.PurchaseService;
import com.enershare.service.user.UsernameService;
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
    private final UsernameService usernameService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPurchase(@Valid @RequestBody PurchaseDTO purchaseDTO,
                                               @RequestHeader(value = "Authorization", required = false) String authHeader) {
       return  purchaseService.createPurchaseWithBasicAuthentication(authHeader,()->purchaseService.createPurchase(purchaseDTO));
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
        String username = usernameService.getUsernameFromRequest(request);
        List<Purchase> purchases = purchaseService.getPurchasesByUsername(username);
        return purchases.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(purchases);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Purchase>> getPurchasesByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String username = usernameService.getUsernameFromRequest(request);
        Page<Purchase> purchases = purchaseService.getPurchasesByCriteria(username, searchRequestDTO);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Purchase>> getLatestPurchases(HttpServletRequest request, @RequestParam(defaultValue = "5") int count) {
        String username = usernameService.getUsernameFromRequest(request);
        List<Purchase> latestResources = purchaseService.getLatestPurchases(username, count);
        return ResponseEntity.ok(latestResources);
    }

    @GetMapping("/latest-purchased")
    public ResponseEntity<List<Purchase>> getLatestPurchased(HttpServletRequest request, @RequestParam(defaultValue = "5") int count) {
        String username = usernameService.getUsernameFromRequest(request);
        List<Purchase> latestResources = purchaseService.getLatestPurchasedResources(username, count);
        return ResponseEntity.ok(latestResources);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<AmountDTO>> getExpenses(HttpServletRequest request) {
        String username = usernameService.getUsernameFromRequest(request);
        List<AmountDTO> summaries = purchaseService.getExpenses(username);
        return ResponseEntity.ok().body(summaries);
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<AmountDTO>> getIncomes(HttpServletRequest request) {
        String username = usernameService.getUsernameFromRequest(request);
        List<AmountDTO> summaries = purchaseService.getIncomes(username);
        return ResponseEntity.ok().body(summaries);
    }


}
