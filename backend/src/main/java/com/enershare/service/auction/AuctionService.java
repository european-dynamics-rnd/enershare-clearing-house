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

        // Check if an entry with the same hash already exists
        Optional<Auction> existingAuction = auctionRepository.findByHash(auction.getHash());

        if (existingAuction.isPresent()) {
            // Update the existing auction using the mapper
            auctionMapper.updateEntityFromDTO(auctionDTO, existingAuction.get());
            auctionRepository.save(existingAuction.get());
        } else {
            // Save as a new auction
            auctionRepository.save(auction);
        }
    }

    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    public Page<Auction> getProposedAuctionsByCriteria(String email,SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Auction> spec = AuctionSpecification.proposedAuctionsByEmail(email,searchRequestDTO.getSearchCriteriaList());
        return auctionRepository.findAll(spec, pageable);
    }

    public Page<Auction> getWonAuctionsByCriteria(String email,SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Auction> spec = AuctionSpecification.wonAuctionsByEmail(email,searchRequestDTO.getSearchCriteriaList());
        return auctionRepository.findAll(spec, pageable);
    }
    public List<Auction> getAllAuctionsSortedByCreatedOn() {
        return auctionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Auction> getAllAuctionsByStatus(String status) {
        return auctionRepository.findByStatus(status); // Retrieve auctions by status
    }

    public List<Auction> getLatestProposedAuctions(String email ,int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "createdOn")); // Sort by createdOn
        return auctionRepository.findLatestProposedAuctions(email,pageable);
    }

    public List<Auction> getLatestWonAuctions(String email,int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "createdOn")); // Sort by createdOn
        return auctionRepository.findLatestWonAuctions(email,pageable);
    }


}