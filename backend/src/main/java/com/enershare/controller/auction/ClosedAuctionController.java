package com.enershare.controller.auction;

import com.enershare.dto.auction.AuctionDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.model.auction.Auction;
import com.enershare.service.auction.AuctionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auctions/closed")
@RequiredArgsConstructor
public class ClosedAuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ResponseEntity<Void> createClosedAuction(@RequestBody AuctionDTO auctionDTO) {
        auctionService.createAuction(auctionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getClosedAuction(@PathVariable Long id) {
        Optional<Auction> auctionOptional = auctionService.getAuctionById(id);
        return auctionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllClosedAuctions() {
        List<Auction> closedAuctionsList = auctionService.getAllAuctionsByStatus("AuctionClosed"); // Retrieve closed auctions
        return closedAuctionsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(closedAuctionsList);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Auction>> getClosedAuctionsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        Page<Auction> auctions = auctionService.getAuctionsByCriteria(searchRequestDTO);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Auction>> getLatestClosedAuctions(@RequestParam(defaultValue = "5") int count) {
        List<Auction> latestClosedAuctions = auctionService.getLatestAuctionsByStatus("AuctionClosed", count); // Get latest closed auctions
        return ResponseEntity.ok(latestClosedAuctions);
    }
}