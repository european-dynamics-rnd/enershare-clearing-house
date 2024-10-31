package com.enershare.controller.bid;

import com.enershare.dto.bid.BidDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.model.bid.Bid;
import com.enershare.service.bid.BidService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/bids/placed")
@RequiredArgsConstructor
public class PlacedBidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<Void> createPlacedBid(@RequestBody BidDTO bidDTO) {
        // Set the status to open based on the incoming JSON
        bidService.createBid(bidDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getPlacedBid(@PathVariable Long id) {
        Optional<Bid> bidOptional = bidService.getBidById(id);
        return bidOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Bid>> getAllPlacedBids() {
        List<Bid> placedBidsList = bidService.getAllBidsByStatus("BidPlaced"); // Retrieve open bids
        return placedBidsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(placedBidsList);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Bid>> getPlacedBidsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        Page<Bid> bids = bidService.getBidsByCriteria(searchRequestDTO);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Bid>> getLatestPlacedBids(@RequestParam(defaultValue = "5") int count) {
        List<Bid> latestPlacedBids = bidService.getLatestBidsByStatus("BidPlaced", count); // Get latest open bids
        return ResponseEntity.ok(latestPlacedBids);
    }
}
