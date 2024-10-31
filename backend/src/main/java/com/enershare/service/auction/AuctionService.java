package com.enershare.service.auction;

import com.enershare.dto.auction.AuctionDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.filtering.specification.auction.AuctionSpecification;
import com.enershare.mapper.AuctionMapper;
import com.enershare.model.auction.Auction;
import com.enershare.repository.auction.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    public void createAuction(AuctionDTO auctionDTO) {
        Auction auction = auctionMapper.mapAuctionDTOToEntity(auctionDTO);
        auctionRepository.save(auction);
    }

    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    public Page<Auction> getAuctionsByCriteria(SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Auction> spec = AuctionSpecification.filterAuctions(searchRequestDTO.getSearchCriteriaList());
        return auctionRepository.findAll(spec, pageable);
    }

    public List<Auction> getAllAuctionsSortedByCreatedOn() {
        return auctionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Auction> getAllAuctionsByStatus(String status) {
        return auctionRepository.findByStatus(status); // Retrieve auctions by status
    }

    public List<Auction> getLatestAuctionsByStatus(String status, int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "createdOn")); // Sort by createdOn
        return auctionRepository.findLatestAuctions(status, pageable);
    }
}
