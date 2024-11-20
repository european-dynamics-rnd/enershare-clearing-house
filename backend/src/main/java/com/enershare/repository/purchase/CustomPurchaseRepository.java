package com.enershare.repository.purchase;

import com.enershare.dto.purchase.AmountDTO;

import java.util.List;

public interface CustomPurchaseRepository {

    List<AmountDTO> getExpenseLastYearByMonth(String username);

    public List<AmountDTO> getIncomesLastYearByMonth(String username);
}
