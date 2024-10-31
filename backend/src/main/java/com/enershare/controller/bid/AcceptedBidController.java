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
@RequestMapping("/bids/accepted")
@RequiredArgsConstructor
public class AcceptedBidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<Void> createAcceptedBid(@RequestBody BidDTO bidDTO) {
        bidService.createBid(bidDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getAcceptedBid(@PathVariable Long id) {
        Optional<Bid> bidOptional = bidService.getBidById(id);
        return bidOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Bid>> getAllAcceptedBids() {
        List<Bid> acceptedBidsList = bidService.getAllBidsByStatus("BidAccepted"); // Retrieve closed bids
        return acceptedBidsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(acceptedBidsList);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Bid>> getAcceptedBidsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        Page<Bid> bids = bidService.getBidsByCriteria(searchRequestDTO);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Bid>> getLatestAcceptedBids(@RequestParam(defaultValue = "5") int count) {
        List<Bid> latestAcceptedBids = bidService.getLatestBidsByStatus("BidAccepted", count); // Get latest closed bids
        return ResponseEntity.ok(latestAcceptedBids);
    }
}
