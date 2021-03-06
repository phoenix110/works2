package com.cernol.works.web.container;

import com.cernol.works.service.StockItemService;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.Container;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

public class ContainerEdit extends AbstractEditor<Container> {

    @Inject
    private StockItemService stockItemService;

    @Inject
    private ToolsService toolsService;

    @Inject
    private TimeSource timeSource;

    @Inject
    private TextField onHand;

    @Inject
    private TextField currentPrice;

    @Inject
    private TextField currentUsage;

    @Inject
    private Button printBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        printBtn.setAction(new EditorPrintFormAction(this, null));
    }

    @Override
    protected void postInit() {
        super.postInit();

        currentPrice.setValue(stockItemService.getPointInTimeCost(getItem().getId(), timeSource.currentTimestamp()));
        onHand.setValue(stockItemService.getPointInTimeQuantity(getItem().getId(), timeSource.currentTimestamp()));
//        currentUsage.setValue((stockItemService.getPeriodUsage(getItem().getId(), Date.from(toolsService.getNow()))));
    }
}