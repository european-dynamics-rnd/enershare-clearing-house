//package com.enershare.controller.auction;
//
//import com.enershare.dto.auction.AuctionDTO;
//import com.enershare.dto.common.SearchRequestDTO;
//import com.enershare.model.auction.Auction;
//import com.enershare.repository.auction.AuctionRepository;
//import com.enershare.service.auction.AuctionService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/auctions")
//@RequiredArgsConstructor
//public class AuctionController {
//
//    private final AuctionService auctionService;
//    private final AuctionRepository auctionRepository;
//
//    @PostMapping("/create")
//    public ResponseEntity<Void> createAuction(@RequestBody AuctionDTO auctionDTO) {
//        auctionService.createAuction(auctionDTO);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Auction> getAuction(@PathVariable Long id) {
//        Optional<Auction> auctionOptional = auctionService.getAuctionById(id);
//        return auctionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Auction>> getAllAuctions() {
//        List<Auction> auctionsList = auctionService.getAllAuctionsSortedByCreatedOn();
//        return auctionsList.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(auctionsList);
//    }
//
//    @PostMapping("/search")
//    public ResponseEntity<Page<Auction>> getAuctionsByCriteria(HttpServletRequest request, @RequestBody SearchRequestDTO searchRequestDTO) {
//        Page<Auction> auctions = auctionService.getAuctionsByCriteria(searchRequestDTO);
//        return ResponseEntity.ok(auctions);
//    }
//
//    @GetMapping("/latest")
//    public ResponseEntity<List<Auction>> getLatestAuctions(@RequestParam(defaultValue = "5") int count) {
//        List<Auction> latestAuctions = auctionService.getLatestAuctions(count);
//        return ResponseEntity.ok(latestAuctions);
//    }
//}
