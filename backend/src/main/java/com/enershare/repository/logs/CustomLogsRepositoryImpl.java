package com.enershare.repository.logs;

import com.enershare.dto.logs.LogSummaryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class CustomLogsRepositoryImpl implements CustomLogsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LogSummaryDTO> getCustomLogSummaryLastTenHours(String email) {
        String queryStr = "SELECT " +
                "DATE_FORMAT(DATE_SUB(NOW(), INTERVAL seq.hour HOUR), '%b %d %H:00') AS dataLabel, " +
                "DATE_SUB(NOW(), INTERVAL seq.hour HOUR) AS dateRange, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.connector_url " +
                "WHERE l.stage = 'INGRESS' " +
                "AND l.created_on >= DATE_SUB(NOW(), INTERVAL seq.hour HOUR) " +
                "AND l.created_on < DATE_ADD(DATE_SUB(NOW(), INTERVAL seq.hour HOUR), INTERVAL 1 HOUR) " +
                "AND u.email = :email) AS ingressLogCount, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.connector_url " +
                "WHERE l.stage = 'EGRESS' " +
                "AND l.created_on >= DATE_SUB(NOW(), INTERVAL seq.hour HOUR) " +
                "AND l.created_on < DATE_ADD(DATE_SUB(NOW(), INTERVAL seq.hour HOUR), INTERVAL 1 HOUR) " +
                "AND u.email = :email) AS egressLogCount " +
                "FROM " +
                "(SELECT 0 AS hour " +
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
                "UNION ALL SELECT 11 " +
                "UNION ALL SELECT 12 " +
                "UNION ALL SELECT 13 " +
                "UNION ALL SELECT 14 " +
                "UNION ALL SELECT 15 " +
                "UNION ALL SELECT 16 " +
                "UNION ALL SELECT 17 " +
                "UNION ALL SELECT 18 " +
                "UNION ALL SELECT 19 " +
                "UNION ALL SELECT 20 " +
                "UNION ALL SELECT 21 " +
                "UNION ALL SELECT 22 " +
                "UNION ALL SELECT 23) AS seq " +
                "ORDER BY seq.hour DESC";

        Query query = entityManager.createNativeQuery(queryStr, "LogSummaryDTO");
        query.setParameter("email", email);
        return query.getResultList();
    }

    @Override
    public List<LogSummaryDTO> getCustomLogSummary(String email) {
        String queryStr = "SELECT " +
                "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL seq.day DAY), '%b %d') AS dataLabel, " +
                "DATE_SUB(CURDATE(), INTERVAL seq.day DAY) AS dateRange, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.connector_url " +
                "WHERE l.stage = 'INGRESS' " +
                "AND l.created_on >= DATE_SUB(CURDATE(), INTERVAL seq.day DAY) " +
                "AND l.created_on < DATE_ADD(DATE_SUB(CURDATE(), INTERVAL seq.day DAY), INTERVAL 1 DAY) " +
                "AND u.email = :email) AS ingressLogCount, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.connector_url " +
                "WHERE l.stage = 'EGRESS' " +
                "AND l.created_on >= DATE_SUB(CURDATE(), INTERVAL seq.day DAY) " +
                "AND l.created_on < DATE_ADD(DATE_SUB(CURDATE(), INTERVAL seq.day DAY), INTERVAL 1 DAY) " +
                "AND u.email = :email) AS egressLogCount " +
                "FROM " +
                "(SELECT 0 AS day " +
                "UNION ALL SELECT 1 " +
                "UNION ALL SELECT 2 " +
                "UNION ALL SELECT 3 " +
                "UNION ALL SELECT 4 " +
                "UNION ALL SELECT 5 " +
                "UNION ALL SELECT 6 " +
                "UNION ALL SELECT 7 " +
                "UNION ALL SELECT 8 " +
                "UNION ALL SELECT 9 " +
                "UNION ALL SELECT 10) AS seq";

        Query query = entityManager.createNativeQuery(queryStr, "LogSummaryDTO");
        query.setParameter("email", email);
        return query.getResultList();
    }

}