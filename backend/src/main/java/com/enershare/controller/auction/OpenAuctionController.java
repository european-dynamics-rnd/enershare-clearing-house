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
@RequestMapping("/auctions/open")
@RequiredArgsConstructor
public class OpenAuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ResponseEntity<Void> createOpenAuction(@RequestBody AuctionDTO auctionDTO) {
        auctionService.createAuction(auctionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getOpenAuction(@PathVariable Long id) {
        Optional<Auction> auctionOptional = auctionService.getAuctionById(id);
        return auctionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllOpenAuctions() {
        List<Auction> openAuctionsList = auctionService.getAllAuctionsByStatus("AuctionOpened"); // Retrieve open auctions
        return openAuctionsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(openAuctionsList);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Auction>> getOpenAuctionsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        Page<Auction> auctions = auctionService.getAuctionsByCriteria(searchRequestDTO);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Auction>> getLatestOpenAuctions(@RequestParam(defaultValue = "5") int count) {
        List<Auction> latestOpenAuctions = auctionService.getLatestAuctionsByStatus("AuctionOpened", count); // Get latest open auctions
        return ResponseEntity.ok(latestOpenAuctions);
    }
}