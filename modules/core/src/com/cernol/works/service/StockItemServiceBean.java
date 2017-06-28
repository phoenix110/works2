package com.cernol.works.service;

import com.cernol.works.entity.StockIntakeItem;
import com.cernol.works.entity.StockItem;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Service(StockItemService.NAME)
public class StockItemServiceBean implements StockItemService {

    @Inject
    private StockItemWorker stockItemWorker;

    @Override
    public BigDecimal getCurrentCost(UUID stockItemId, Date queryDate) {

        return stockItemWorker.getCurrentCost(stockItemId, queryDate);
    }

    @Override
    public BigDecimal getCurrentQuantity(UUID stockItemId, Date queryDate) {

        return stockItemWorker.getCurrentQuantity(stockItemId, queryDate);

    }


}