package com.enershare.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmountDTO {

    Double price;
    private String dataLabel;
    private Instant monthEnd;


}
