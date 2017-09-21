package com.cernol.works.web.container;

import com.cernol.works.service.StockItemService;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.Container;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import java.util.Date;

public class ContainerEdit extends AbstractEditor<Container> {

    @Inject
    private StockItemService stockItemService;

    @Inject
    private ToolsService toolsService;

    @Inject
    private TextField onHand;

    @Inject
    private TextField currentPrice;

    @Inject
    private TextField currentUsage;

    @Override
    protected void postInit() {
        super.postInit();

        currentPrice.setValue(stockItemService.getPointInTimeCost(getItem().getId(), Date.from(toolsService.getNow())));
        onHand.setValue((stockItemService.getPointInTimeQuantity(getItem().getId(), Date.from(toolsService.getNow()))));
        currentUsage.setValue((stockItemService.getPeriodUsage(getItem().getId(), Date.from(toolsService.getNow()))));
    }
}