package com.enershare.controller.auction;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.model.auction.Auction;
import com.enershare.service.auction.AuctionService;
import com.enershare.service.user.UsernameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final UsernameService usernameService;

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuction(@PathVariable Long id) {
        Optional<Auction> auctionOptional = auctionService.getAuctionById(id);
        return auctionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctionsSortedByCreatedOn();
        return auctions.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(auctions);
    }

    @PostMapping("/search-proposed")
    public ResponseEntity<Page<Auction>> getProposedAuctionsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String username = usernameService.getUsernameFromRequest(request);
        Page<Auction> auctions = auctionService.getProposedAuctionsByCriteria(username, searchRequestDTO);
        return ResponseEntity.ok(auctions);
    }

    @PostMapping("/search-won")
    public ResponseEntity<Page<Auction>> getWonAuctionsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
        String email = usernameService.getUsernameFromRequest(request);
        Page<Auction> auctions = auctionService.getWonAuctionsByCriteria(email, searchRequestDTO);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/latest-proposed")
    public ResponseEntity<List<Auction>> getLatestProposedAuctions(HttpServletRequest request, @RequestParam(defaultValue = "5") int count) {
        String email = usernameService.getUsernameFromRequest(request);
        List<Auction> latestOpenAuctions = auctionService.getLatestProposedAuctions(email, count);
        return ResponseEntity.ok(latestOpenAuctions);
    }

    @GetMapping("/latest-won")
    public ResponseEntity<List<Auction>> getLatestWonAuctions(HttpServletRequest request, @RequestParam(defaultValue = "5") int count) {
        String email = usernameService.getUsernameFromRequest(request);
        List<Auction> latestOpenAuctions = auctionService.getLatestWonAuctions(email, count);
        return ResponseEntity.ok(latestOpenAuctions);
    }

}
