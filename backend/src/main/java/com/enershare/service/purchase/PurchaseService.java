package com.enershare.service.purchase;

import com.enershare.dto.common.SearchRequestDTO;
import com.enershare.dto.purchase.AmountDTO;
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

    public List<Purchase> getPurchasesByUsername(String username) {
        return purchaseRepository.findPurchasedByUsername(username);
    }

    public Page<Purchase> getPurchasesByCriteria(String username, SearchRequestDTO searchRequestDTO) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(searchRequestDTO.getDirection()), searchRequestDTO.getSort());
        Pageable pageable = PageRequest.of(searchRequestDTO.getPage(), searchRequestDTO.getPageSize(), sortOrder);
        Specification<Purchase> spec = PurchaseSpecification.purchasesByUsername(username, searchRequestDTO.getSearchCriteriaList());
        return purchaseRepository.findAll(spec, pageable);
    }

    public List<Purchase> getLatestPurchases(String username, int count) {
        return purchaseRepository.findLatestPurchases(username, PageRequest.of(0, count));
    }

    public List<Purchase> getLatestPurchasedResources(String username, int count) {
        return purchaseRepository.findLatestPurchasedResources(username, PageRequest.of(0, count));
    }

    public List<AmountDTO> getExpenses(String username) {
        return purchaseRepository.getExpenseLastYearByMonth(username);
    }

    public List<AmountDTO> getIncomes(String username) {
        return purchaseRepository.getIncomesLastYearByMonth(username);
    }
}
