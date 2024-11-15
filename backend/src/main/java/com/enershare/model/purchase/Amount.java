package com.enershare.model.purchase;

import com.enershare.dto.logs.LogSummaryDTO;
import com.enershare.dto.purchase.AmountDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@SqlResultSetMapping(
        name = "MonthlyAmountSummaryDTOMapping",
        classes = @ConstructorResult(
                targetClass = AmountDTO.class,
                columns = {
                        @ColumnResult(name = "dataLabel", type = String.class),
                        @ColumnResult(name = "monthEnd", type = Instant.class),
                        @ColumnResult(name = "price", type = Double.class)
                }
        )
)
public class Amount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
