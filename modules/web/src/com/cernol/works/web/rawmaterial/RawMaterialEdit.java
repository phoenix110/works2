package com.cernol.works.web.rawmaterial;

import com.cernol.works.service.StockItemService;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.RawMaterial;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import java.util.Date;

public class RawMaterialEdit extends AbstractEditor<RawMaterial> {

    @Inject
    private StockItemService stockItemService;

    @Inject
    private ToolsService toolsService;

    @Inject
    private TextField onHand;

    @Inject
    private TextField currentPrice;

    @Override
    protected void postInit() {
        super.postInit();

        currentPrice.setValue(stockItemService.getCurrentCost(getItem().getId(), Date.from(toolsService.getNow())));
        onHand.setValue((stockItemService.getCurrentQuantity(getItem().getId(), Date.from(toolsService.getNow()))));

    }
}