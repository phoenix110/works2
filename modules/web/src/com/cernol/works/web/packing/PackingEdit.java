package com.cernol.works.web.packing;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.Packing;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;

public class PackingEdit extends AbstractEditor<Packing> {

    @Inject
    private StockItemService stockItemService;

    @Inject
    private TimeSource timeSource;

    @Inject
    private TextField onHand;

    @Inject
    private TextField currentPrice;

    @Inject
    private TextField currentUsage;

    @Override
    protected void postInit() {
        super.postInit();

        currentPrice.setValue(stockItemService.getPointInTimeCost(getItem().getId(), timeSource.currentTimestamp()));
        onHand.setValue(stockItemService.getPointInTimeQuantity(getItem().getId(), timeSource.currentTimestamp()));
    }
}