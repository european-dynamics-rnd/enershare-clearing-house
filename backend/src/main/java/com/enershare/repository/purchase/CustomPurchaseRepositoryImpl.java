package com.enershare.repository.purchase;

import com.enershare.dto.purchase.AmountDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class CustomPurchaseRepositoryImpl implements CustomPurchaseRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AmountDTO> getExpenseLastYearByMonth(String email) {
        String queryStr = "SELECT " +
                "DATE_FORMAT(DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH), '%b %Y') AS dataLabel, " +
                "DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) AS monthStart, " +
                "DATE_SUB(DATE_FORMAT(DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) AS monthEnd, " +
                "(SELECT COALESCE(SUM(p.price), 0) FROM purchases p " +
                "JOIN user u ON u.participant_id = p.consumer_participant_id " +
                "WHERE p.created_on >= DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) " +
                "AND p.created_on < DATE_SUB(DATE_FORMAT(DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH)" +
                "AND u.email = :email) AS price " +
                "FROM " +
                "(SELECT 0 AS month " +
                "UNION ALL SELECT 1 " +
                "UNION ALL SELECT 2 " +
                "UNION ALL SELECT 3 " +
                "UNION ALL SELECT 4 " +
                "UNION ALL SELECT 5 " +
                "UNION ALL SELECT 6 " +
                "UNION ALL SELECT 7 " +
                "UNION ALL SELECT 8 " +
                "UNION ALL SELECT 9 " +
                "UNION ALL SELECT 10 " +
                "UNION ALL SELECT 11 ) AS seq " +
                "ORDER BY seq.month DESC";

        Query query = entityManager.createNativeQuery(queryStr, "MonthlyAmountSummaryDTOMapping");
        query.setParameter("email", email);
        return query.getResultList();
    }


    @Override
    public List<AmountDTO> getIncomesLastYearByMonth(String email) {
        String queryStr = "SELECT " +
                "DATE_FORMAT(DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH), '%b %Y') AS dataLabel, " +
                "DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) AS monthStart, " +
                "DATE_SUB(DATE_FORMAT(DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) AS monthEnd, " +
                "(SELECT COALESCE(SUM(p.price), 0) " +
                " FROM purchases p " +
                " JOIN resources r ON r.resource_id = p.resource_id " +
                " JOIN user u ON u.participant_id = r.provider_participant_id " +
                " WHERE p.created_on >= DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) " +
                " AND p.created_on < DATE_SUB(DATE_FORMAT(DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 MONTH), '%Y-%m-01 00:00:00'), INTERVAL seq.month MONTH) " +
                " AND u.email = :email) AS price " +
                "FROM " +
                "(SELECT 0 AS month " +
                "UNION ALL SELECT 1 " +
                "UNION ALL SELECT 2 " +
                "UNION ALL SELECT 3 " +
                "UNION ALL SELECT 4 " +
                "UNION ALL SELECT 5 " +
                "UNION ALL SELECT 6 " +
                "UNION ALL SELECT 7 " +
                "UNION ALL SELECT 8 " +
                "UNION ALL SELECT 9 " +
                "UNION ALL SELECT 10 " +
                "UNION ALL SELECT 11 ) AS seq " +
                "ORDER BY seq.month DESC";

        Query query = entityManager.createNativeQuery(queryStr, "MonthlyAmountSummaryDTOMapping");
        query.setParameter("email", email);
        return query.getResultList();
    }
}
