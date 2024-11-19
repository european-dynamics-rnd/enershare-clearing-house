package com.enershare.filtering.specification.log;

import com.enershare.filtering.SearchCriteria;
import com.enershare.model.logs.Logs;
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

public class LogsSpecification {


    public static Specification<Logs> ingressLogsByUsername(String username, List<SearchCriteria> searchCriteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add the existing logic for filtering based on email
            Subquery<User> userSubquery = query.subquery(User.class);
            Root<User> userRoot = userSubquery.from(User.class);
            userSubquery.select(userRoot);
            userSubquery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(userRoot.get("connectorUrl"), root.get("consumer")),
                    criteriaBuilder.equal(userRoot.get("username"), username),
                    criteriaBuilder.equal(root.get("mode"), "INGRESS")
            ));
            return getPredicate(searchCriteriaList, root, criteriaBuilder, predicates, userSubquery);
        };
    }

    public static Specification<Logs> egressLogsByUsername(String username, List<SearchCriteria> searchCriteriaList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add the existing logic for filtering based on email
            Subquery<User> userSubquery = query.subquery(User.class);
            Root<User> userRoot = userSubquery.from(User.class);
            userSubquery.select(userRoot);
            userSubquery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(userRoot.get("connectorUrl"), root.get("provider")),
                    criteriaBuilder.equal(userRoot.get("username"), username),
                    criteriaBuilder.equal(root.get("mode"), "EGRESS")
            ));
            return getPredicate(searchCriteriaList, root, criteriaBuilder, predicates, userSubquery);
        };
    }

    private static Predicate getPredicate(List<SearchCriteria> searchCriteriaList, Root<Logs> root, CriteriaBuilder criteriaBuilder,
                                          List<Predicate> predicates, Subquery<User> userSubquery) {
        predicates.add(criteriaBuilder.exists(userSubquery));


        searchCriteriaList.forEach(searchCriteria -> {
            Predicate predicate;
            if (searchCriteria.getColumnValue() instanceof String) {
                if ("createdOn".equals(searchCriteria.getColumnName())) {
                    LocalDateTime dateTimeValue = LocalDateTime.parse((String) searchCriteria.getColumnValue(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDate date = dateTimeValue.toLocalDate();
                    predicate = criteriaBuilder.equal(criteriaBuilder.function("DATE", LocalDate.class, root.get(searchCriteria.getColumnName())), date);
                } else {

                    predicate = criteriaBuilder.like(root.get(searchCriteria.getColumnName()), "%" + searchCriteria.getColumnValue() + "%");
                }
            } else {
                // Use 'equal' for other types of values
                predicate = criteriaBuilder.equal(root.get(searchCriteria.getColumnName()), searchCriteria.getColumnValue());
            }
            predicates.add(predicate);
        });

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
