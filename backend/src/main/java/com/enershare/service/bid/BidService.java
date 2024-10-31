package com.enershare.service.bid;

import com.enershare.dto.bid.BidDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.filtering.specification.bid.BidSpecification;
import com.enershare.mapper.BidMapper;
import com.enershare.model.bid.Bid;
import com.enershare.repository.bid.BidRepository;
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
public class BidService {

    private final BidRepository bidRepository;
    private final BidMapper bidMapper;

    public void createBid(BidDTO bidDTO) {
        Bid bid = bidMapper.mapBidDTOToEntity(bidDTO);
        bidRepository.save(bid);
    }

    public Optional<Bid> getBidById(Long id) {
        return bidRepository.findById(id);
    }

    public Page<Bid> getBidsByCriteria(SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Bid> spec = BidSpecification.filterBids(searchRequestDTO.getSearchCriteriaList());
        return bidRepository.findAll(spec, pageable);
    }

    public List<Bid> getAllBidsSortedByCreatedOn() {
        return bidRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Bid> getAllBidsByStatus(String status) {
        return bidRepository.findByStatus(status);
    }

    public List<Bid> getLatestBidsByStatus(String status, int count) {
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "createdOn")); // Sort by createdOn
        return bidRepository.findLatestBids(status, pageable);
    }
}
