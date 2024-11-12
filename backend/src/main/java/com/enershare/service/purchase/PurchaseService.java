package com.enershare.service.purchase;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.purchase.PurchaseDTO;
import com.enershare.filtering.specification.purchase.PurchaseSpecification;
import com.enershare.mapper.PurchaseMapper;
import com.enershare.model.purchase.Purchase;
import com.enershare.repository.purchase.PurchaseRepository;
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
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;

    public void createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = purchaseMapper.mapDTOToEntity(purchaseDTO);
        purchaseRepository.save(purchase);
    }

    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    public List<Purchase> getAllPurchasesSortedByCreatedOn() {
        return purchaseRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    public List<Purchase> getPurchasesByUserEmail(String email) {
        return purchaseRepository.findPurchasedByUserEmail(email);
    }

    public Page<Purchase> getPurchasesByCriteria(String email, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Purchase> spec = PurchaseSpecification.purchasesByEmail(email,searchRequestDTO.getSearchCriteriaList());
        return purchaseRepository.findAll(spec, pageable);
    }

    public List<Purchase> getLatestPurchases(String email, int count) {
        return purchaseRepository.findLatestPurchases(email, PageRequest.of(0, count));
    }
}
