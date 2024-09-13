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
                "DATE_FORMAT(DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour) HOUR), '%b %d %H:00') AS dataLabel, " +
                "DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour) HOUR) AS dateRange, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.consumer " +
                "WHERE l.created_on >= DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour) HOUR) " +
                "AND l.created_on < DATE_SUB(DATE_FORMAT(DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 HOUR), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour) HOUR) " +
                "AND l.mode = 'INGRESS'" +
                "AND u.email = :email) AS ingressLogCount, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.provider " +
                "WHERE l.created_on >= DATE_SUB(DATE_FORMAT(UTC_TIMESTAMP(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour) HOUR) " +
                "AND l.created_on < DATE_SUB(DATE_FORMAT(DATE_ADD(UTC_TIMESTAMP(), INTERVAL 1 HOUR), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour) HOUR) " +
                "AND l.mode = 'EGRESS'" +
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
                "UNION ALL SELECT 9 ) AS seq " +
                "ORDER BY seq.hour DESC";

        Query query = entityManager.createNativeQuery(queryStr, "LogSummaryDTOMapping");
        query.setParameter("email", email);
        return query.getResultList();
    }

//    @Override
//    public List<LogSummaryDTO> getCustomLogSummaryLastTenHours(String email) {
//        String queryStr = "SELECT " +
//                "DATE_FORMAT(DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour + 1) HOUR), '%b %d %H:00') AS dataLabel, " +
//                "DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour + 1) HOUR) AS dateRange, " +
//                "(SELECT COUNT(*) FROM logs l " +
//                "JOIN user u ON u.connector_url = l.consumer " +
//                "WHERE l.created_on >= DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour + 1) HOUR) " +
//                "AND l.created_on < DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), INTERVAL seq.hour HOUR) " +
//                "AND u.email = :email) AS ingressLogCount, " +
//                "(SELECT COUNT(*) FROM logs l " +
//                "JOIN user u ON u.connector_url = l.provider " +
//                "WHERE l.created_on >= DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), INTERVAL (seq.hour + 1) HOUR) " +
//                "AND l.created_on < DATE_SUB(DATE_FORMAT(NOW(), '%Y-%m-%d %H:00:00'), INTERVAL seq.hour HOUR) " +
//                "AND u.email = :email) AS egressLogCount " +
//                "FROM " +
//                "(SELECT 0 AS hour " +
//                "UNION ALL SELECT 1 " +
//                "UNION ALL SELECT 2 " +
//                "UNION ALL SELECT 3 " +
//                "UNION ALL SELECT 4 " +
//                "UNION ALL SELECT 5 " +
//                "UNION ALL SELECT 6 " +
//                "UNION ALL SELECT 7 " +
//                "UNION ALL SELECT 8 " +
//                "UNION ALL SELECT 9 ) AS seq " +
//                "ORDER BY seq.hour DESC";
//
//        Query query = entityManager.createNativeQuery(queryStr, "LogSummaryDTOMapping");
//        query.setParameter("email", email);
//        return query.getResultList();
//    }


    @Override
    public List<LogSummaryDTO> getCustomLogSummary(String email) {
        String queryStr = "SELECT " +
                "DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL seq.day DAY), '%b %d') AS dataLabel, " +
                "DATE_SUB(CURDATE(), INTERVAL seq.day DAY) AS dateRange, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.consumer " +
                "WHERE l.created_on >= DATE_SUB(CURDATE(), INTERVAL seq.day DAY) " +
                "AND l.created_on < DATE_ADD(DATE_SUB(CURDATE(), INTERVAL seq.day DAY), INTERVAL 1 DAY) " +
                "AND l.mode = 'INGRESS'" +
                "AND u.email = :email) AS ingressLogCount, " +
                "(SELECT COUNT(*) FROM logs l " +
                "JOIN user u ON u.connector_url = l.provider " +
                "WHERE  l.created_on >= DATE_SUB(CURDATE(), INTERVAL seq.day DAY) " +
                "AND l.created_on < DATE_ADD(DATE_SUB(CURDATE(), INTERVAL seq.day DAY), INTERVAL 1 DAY) " +
                "AND l.mode = 'EGRESS'" +
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

        Query query = entityManager.createNativeQuery(queryStr, "LogSummaryDTOMapping");
        query.setParameter("email", email);
        return query.getResultList();
    }


}
