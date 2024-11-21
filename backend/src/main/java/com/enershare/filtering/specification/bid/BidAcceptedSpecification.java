package com.enershare.filtering.specification.bid;

import com.enershare.filtering.SearchCriteria;
import com.enershare.model.bid.BidAccepted;
import com.enershare.model.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BidAcceptedSpecification {

    public static Specification<BidAccepted> bidsAcceptedByUsername(String username, List<SearchCriteria> searchCriteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Subquery to match accepted bids by username via consumerParticipantId
            Subquery<User> userSubquery = query.subquery(User.class);
            Root<User> userRoot = userSubquery.from(User.class);
            userSubquery.select(userRoot);
            userSubquery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(userRoot.get("participantId"), root.get("consumerParticipantId")),
                    criteriaBuilder.equal(userRoot.get("username"), username)
            ));

            // Add user subquery as predicate
            predicates.add(criteriaBuilder.exists(userSubquery));

            // Add filtering based on search criteria
            addSearchCriteriaPredicates(searchCriteriaList, root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addSearchCriteriaPredicates(List<SearchCriteria> searchCriteriaList, Root<BidAccepted> root,
                                                    CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        searchCriteriaList.forEach(searchCriteria -> {
            Predicate predicate;
            if (searchCriteria.getColumnValue() instanceof String) {
                if ("createdOn".equals(searchCriteria.getColumnName())) {
                    // Filter by date (assumes ISO_DATE_TIME format for value)
                    LocalDateTime dateTimeValue = LocalDateTime.parse((String) searchCriteria.getColumnValue(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDate date = dateTimeValue.toLocalDate();
                    predicate = criteriaBuilder.equal(criteriaBuilder.function("DATE", LocalDate.class, root.get(searchCriteria.getColumnName())), date);
                } else {
                    // Generic case for string matching
                    predicate = criteriaBuilder.like(root.get(searchCriteria.getColumnName()), "%" + searchCriteria.getColumnValue() + "%");
                }
            } else {
                // Generic case for non-string equality
                predicate = criteriaBuilder.equal(root.get(searchCriteria.getColumnName()), searchCriteria.getColumnValue());
            }
            predicates.add(predicate);
        });
    }
}
