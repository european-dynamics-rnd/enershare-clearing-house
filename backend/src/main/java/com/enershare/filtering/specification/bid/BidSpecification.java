package com.enershare.filtering.specification.bid;

import com.enershare.filtering.SearchCriteria;
import com.enershare.model.bid.Bid;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BidSpecification {

    public static Specification<Bid> bidsByUsername(List<SearchCriteria> searchCriteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            searchCriteriaList.forEach(searchCriteria -> {
                Predicate predicate;
                switch (searchCriteria.getColumnName()) {
                    case "resourceID":
                        predicate = criteriaBuilder.equal(root.get("resourceId"), searchCriteria.getColumnValue());
                        break;
                    case "providerConnectorID":
                        predicate = criteriaBuilder.equal(root.get("providerConnectorId"), searchCriteria.getColumnValue());
                        break;
                    case "providerParticipantIDSID":
                        predicate = criteriaBuilder.equal(root.get("providerParticipantId"), searchCriteria.getColumnValue());
                        break;
                    case "consumerParticipantIDSID":
                        predicate = criteriaBuilder.equal(root.get("consumerParticipantId"), searchCriteria.getColumnValue());
                        break;
                    case "free":
                        predicate = criteriaBuilder.equal(root.get("free"), searchCriteria.getColumnValue());
                        break;
                    case "artifactsIds":
                        // Assuming 'artifactsIds' is a collection
                        predicate = root.get("artifactsIds").in(searchCriteria.getColumnValue());
                        break;
                    case "auctionHash":
                        predicate = criteriaBuilder.equal(root.get("auctionHash"), searchCriteria.getColumnValue());
                        break;
                    case "hash":
                        predicate = criteriaBuilder.equal(root.get("hash"), searchCriteria.getColumnValue());
                        break;
                    case "status":
                        predicate = criteriaBuilder.equal(root.get("status"), searchCriteria.getColumnValue());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid column name: " + searchCriteria.getColumnName());
                }
                predicates.add(predicate);
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
