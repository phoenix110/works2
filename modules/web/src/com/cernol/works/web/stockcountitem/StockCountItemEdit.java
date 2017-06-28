package com.cernol.works.web.stockcountitem;

import com.cernol.works.service.StockItemService;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.StockCountItem;
import com.haulmont.cuba.gui.components.LookupPickerField;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Date;

public class StockCountItemEdit extends AbstractEditor<StockCountItem> {

    @Inject
    private StockItemService stockItemService;

    @Inject
    private ToolsService toolsService;

    @Named("fieldGroup.stockItem")
    private LookupPickerField stockItemField;

    @Override
    protected void postInit() {
        super.postInit();

        stockItemField.addValueChangeListener(e -> stockItemChanged());


    }



    private void stockItemChanged() {
        BigDecimal currentCost = BigDecimal.ZERO;

        StockCountItem mySCI = getItem();
/*

        currentCost = stockItemService.getCurrentCost(
                mySCI.getStockItem().getId(),
                mySCI.getStockCount().getDocumentOn());
*/

        getItem().setCurrentValue(currentCost);

    }

}