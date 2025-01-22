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
@RequestMapping("/auctions/open")
@RequiredArgsConstructor
public class OpenAuctionController {

    private final AuctionService auctionService;

    @PostMapping("/create")
    public ResponseEntity<Void> createOpenAuction(@RequestBody AuctionDTO auctionDTO,
                                                  @RequestHeader(value = "Authorization", required = false) String authHeader) {
        return auctionService.createAuctionWithBasicAuthentication(authHeader, () -> auctionService.createAuction(auctionDTO));
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllOpenAuctions() {
        List<Auction> openAuctionsList = auctionService.getAllAuctionsByStatus("AuctionOpened"); // Retrieve open auctions
        return openAuctionsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(openAuctionsList);
    }


}