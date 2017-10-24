package com.cernol.works.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface StockItemService {
    String NAME = "works_StockItemService";

    BigDecimal getPointInTimeCost(UUID stockItemId, Date queryDate);

    BigDecimal getPointInTimeQuantity(UUID stockItemId, Date queryDate);

    BigDecimal getPeriodUsage(UUID stockItemId, Date queryDate);
    
}