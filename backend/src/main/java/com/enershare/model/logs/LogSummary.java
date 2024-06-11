package com.enershare.model.logs;

import com.enershare.dto.logs.LogSummaryDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@SqlResultSetMapping(
        name = "LogSummaryDTOMapping",
        classes = @ConstructorResult(
                targetClass = LogSummaryDTO.class,
                columns = {
                        @ColumnResult(name = "dataLabel", type = String.class),
                        @ColumnResult(name = "dateRange", type = Instant.class),
                        @ColumnResult(name = "ingressLogCount", type = Integer.class),
                        @ColumnResult(name = "egressLogCount", type = Integer.class)
                }
        )
)
public class LogSummary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
