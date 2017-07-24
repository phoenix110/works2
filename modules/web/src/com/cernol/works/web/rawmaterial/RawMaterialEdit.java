package com.cernol.works.web.rawmaterial;

import com.cernol.works.service.StockItemService;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.RawMaterial;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

public class RawMaterialEdit extends AbstractEditor<RawMaterial> {

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

    @Inject
    private Button printBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        printBtn.setAction(new EditorPrintFormAction(this,null));

    }

    @Override
    protected void postInit() {
        super.postInit();

        currentPrice.setValue(stockItemService.getPointInTimeCost(getItem().getId(), Date.from(toolsService.getNow())));
        onHand.setValue((stockItemService.getPointInTimeQuantity(getItem().getId(), Date.from(toolsService.getNow()))));
        currentUsage.setValue((stockItemService.getPeriodUsage(getItem().getId(), Date.from(toolsService.getNow()))));

    }
}