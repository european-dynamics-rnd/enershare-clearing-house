package com.enershare.service.bid;

import com.enershare.dto.bid.BidPlacedDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.filtering.specification.bid.BidPlacedSpecification;
import com.enershare.mapper.BidPlacedMapper;
import com.enershare.model.bid.BidPlaced;
import com.enershare.repository.bid.BidPlacedRepository;
import com.enershare.service.auth.BasicAuthenticationService;
import com.enershare.service.user.UsernameService;
import com.enershare.utils.RunnableWithReturn;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidPlacedService {

    private final BidPlacedRepository bidPlacedRepository;
    private final BidPlacedMapper bidPlacedMapper;
    private final UsernameService usernameService;
    private final BasicAuthenticationService basicAuthenticationService;

    public ResponseEntity<Void> createBidPlaced(BidPlacedDTO bidPlacedDTO) {
        BidPlaced bidPlaced = bidPlacedMapper.mapDTOToEntity(bidPlacedDTO);
        bidPlacedRepository.save(bidPlaced);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Optional<BidPlaced> getBidPlacedById(Long id) {
        return bidPlacedRepository.findById(id);
    }

    public Page<BidPlaced> getBidsPlacedByCriteria(String username, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<BidPlaced> spec = BidPlacedSpecification.bidsPlacedByUsername(username, searchRequestDTO.getSearchCriteriaList());
        return bidPlacedRepository.findAll(spec, pageable);
    }

    public List<BidPlaced> getAllBidsPlacedSortedByCreatedOn() {
        return bidPlacedRepository.findAllBidsPlacedSortedByCreatedOn();
    }

    public List<BidPlaced> getLatestBidsPlaced(int count) {
        Pageable pageable = PageRequest.of(0, count);
        return bidPlacedRepository.findLatestBidsPlaced(pageable);
    }

    public List<BidPlaced> getBidsPlacedByUsername(String username) {
        return bidPlacedRepository.findBidsPlacedByUsername(username);
    }

    public List<BidPlaced> getBidsPlacedByUser(HttpServletRequest request) {
        String username = usernameService.getUsernameFromRequest(request);
        return bidPlacedRepository.findBidsPlacedByUsername(username);
    }

    public ResponseEntity<Void> createBidPlacedWithBasicAuthentication(String authHeader, RunnableWithReturn<ResponseEntity<Void>> createBidPlacedAction) {
        return basicAuthenticationService.authenticateAndExecute(authHeader, createBidPlacedAction);
    }
}
