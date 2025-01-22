package com.enershare.controller.bid;

import com.enershare.dto.bid.BidPlacedDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.model.bid.BidPlaced;
import com.enershare.service.bid.BidPlacedService;
import com.enershare.service.user.UsernameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bids/placed")
@RequiredArgsConstructor
public class BidPlacedController {

    private final BidPlacedService bidPlacedService;
    private final UsernameService usernameService;

    @PostMapping("/create")
    public ResponseEntity<Void> createBidPlaced(@RequestBody BidPlacedDTO bidPlacedDTO,
                                                @RequestHeader(value = "Authorization", required = false) String authHeader) {
        return bidPlacedService.createBidPlacedWithBasicAuthentication(authHeader, () -> bidPlacedService.createBidPlaced(bidPlacedDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidPlaced> getBidPlaced(@PathVariable Long id) {
        Optional<BidPlaced> bidPlacedOptional = bidPlacedService.getBidPlacedById(id);
        return bidPlacedOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BidPlaced>> getAllBidsPlaced() {
        List<BidPlaced> placedBids = bidPlacedService.getAllBidsPlacedSortedByCreatedOn();
        return placedBids.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(placedBids);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<BidPlaced>> getBidsPlacedByUser(HttpServletRequest request) {
        List<BidPlaced> placedBids = bidPlacedService.getBidsPlacedByUser(request);
        return placedBids.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(placedBids);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<BidPlaced>> getBidsPlacedByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String username = usernameService.getUsernameFromRequest(request);
        Page<BidPlaced> placedBids = bidPlacedService.getBidsPlacedByCriteria(username, searchRequestDTO);
        return ResponseEntity.ok(placedBids);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<BidPlaced>> getLatestBidsPlaced(@RequestParam(defaultValue = "5") int count) {
        List<BidPlaced> latestPlacedBids = bidPlacedService.getLatestBidsPlaced(count);
        return ResponseEntity.ok(latestPlacedBids);
    }
}
