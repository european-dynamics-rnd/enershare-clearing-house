package com.enershare.repository.purchase;

import com.enershare.dto.purchase.AmountDTO;

import java.util.List;

public interface CustomPurchaseRepository {

    List<AmountDTO> getExpenseLastYearByMonth(String email);

    public List<AmountDTO> getIncomesLastYearByMonth(String email);
}
