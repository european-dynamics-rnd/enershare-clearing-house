package com.enershare.controller.auction;

import com.enershare.dto.auction.AuctionDTO;
import com.enershare.model.auction.Auction;
import com.enershare.service.auction.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions/closed")
@RequiredArgsConstructor
public class ClosedAuctionController {

    private final AuctionService auctionService;

    @PostMapping("/create")
    public ResponseEntity<Void> createClosedAuction(@RequestBody AuctionDTO auctionDTO,
                                                    @RequestHeader(value = "Authorization", required = false) String authHeader) {
        return auctionService.createAuctionWithBasicAuthentication(authHeader,()-> auctionService.createAuction(auctionDTO));

    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllClosedAuctions() {
        List<Auction> closedAuctionsList = auctionService.getAllAuctionsByStatus("AuctionClosed"); // Retrieve closed auctions
        return closedAuctionsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(closedAuctionsList);
    }


}