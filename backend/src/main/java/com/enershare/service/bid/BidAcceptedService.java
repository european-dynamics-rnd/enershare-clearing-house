package com.enershare.service.bid;

import com.enershare.dto.bid.BidAcceptedDTO;
import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.filtering.specification.bid.BidAcceptedSpecification;
import com.enershare.mapper.BidAcceptedMapper;
import com.enershare.model.bid.BidAccepted;
import com.enershare.repository.bid.BidAcceptedRepository;
import com.enershare.service.user.UsernameService;
import jakarta.servlet.http.HttpServletRequest;
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
public class BidAcceptedService {

    private final BidAcceptedRepository bidAcceptedRepository;
    private final BidAcceptedMapper bidAcceptedMapper;
    private final UsernameService usernameService;

    public void createBidAccepted(BidAcceptedDTO bidAcceptedDTO) {
        BidAccepted bidAccepted = bidAcceptedMapper.mapDTOToEntity(bidAcceptedDTO);
        bidAcceptedRepository.save(bidAccepted);
    }

    public Optional<BidAccepted> getBidAcceptedById(Long id) {
        return bidAcceptedRepository.findById(id);
    }

    public Page<BidAccepted> getBidsAcceptedByCriteria(String username, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<BidAccepted> spec = BidAcceptedSpecification.bidsAcceptedByUsername(username, searchRequestDTO.getSearchCriteriaList());
        return bidAcceptedRepository.findAll(spec, pageable);
    }

    public List<BidAccepted> getAllBidsAcceptedSortedByCreatedOn() {
        return bidAcceptedRepository.findAllBidsAcceptedSortedByCreatedOn();
    }

    public List<BidAccepted> getLatestBidsAccepted(int count) {
        Pageable pageable = PageRequest.of(0, count);
        return bidAcceptedRepository.findLatestBidsAccepted(pageable);
    }

    public List<BidAccepted> getBidsAcceptedByUsername(String username) {
        return bidAcceptedRepository.findBidsAcceptedByUsername(username);
    }

    public List<BidAccepted> getBidsAcceptedByUser(HttpServletRequest request) {
        String username = usernameService.getUsernameFromRequest(request);
        return bidAcceptedRepository.findBidsAcceptedByUsername(username);
    }
}
