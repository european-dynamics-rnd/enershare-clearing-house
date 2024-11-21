package com.enershare.controller.bid;

import com.enershare.dto.bid.BidAcceptedDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.model.bid.BidAccepted;
import com.enershare.service.bid.BidAcceptedService;
import com.enershare.service.user.UsernameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bids/accepted")
@RequiredArgsConstructor
public class BidAcceptedController {

    private final BidAcceptedService bidAcceptedService;
    private final UsernameService usernameService;

    @PostMapping("/create")
    public ResponseEntity<Void> createBidAccepted(@RequestBody BidAcceptedDTO bidAcceptedDTO) {
        bidAcceptedService.createBidAccepted(bidAcceptedDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidAccepted> getBidAccepted(@PathVariable Long id) {
        Optional<BidAccepted> bidAcceptedOptional = bidAcceptedService.getBidAcceptedById(id);
        return bidAcceptedOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BidAccepted>> getAllBidsAccepted() {
        List<BidAccepted> acceptedBids = bidAcceptedService.getAllBidsAcceptedSortedByCreatedOn();
        return acceptedBids.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(acceptedBids);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<BidAccepted>> getBidsAcceptedByUser(HttpServletRequest request) {
        List<BidAccepted> acceptedBids = bidAcceptedService.getBidsAcceptedByUser(request);
        return acceptedBids.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(acceptedBids);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<BidAccepted>> getBidsAcceptedByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String username = usernameService.getUsernameFromRequest(request);
        Page<BidAccepted> acceptedBids = bidAcceptedService.getBidsAcceptedByCriteria(username, searchRequestDTO);
        return ResponseEntity.ok(acceptedBids);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<BidAccepted>> getLatestBidsAccepted(@RequestParam(defaultValue = "5") int count) {
        List<BidAccepted> latestAcceptedBids = bidAcceptedService.getLatestBidsAccepted(count);
        return ResponseEntity.ok(latestAcceptedBids);
    }
}
