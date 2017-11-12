package com.cernol.works.web.stockcountitem;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.StockCountItem;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

public class StockCountItemEdit extends AbstractEditor<StockCountItem> {

    @Inject
    private StockItemService stockItemService;

    @Named("fieldGroup.stockItem")
    private LookupPickerField stockItemField;

    @Named("fieldGroup.countedQuantity")
    private TextField countedQuantityField;

    @Override
    protected void postInit() {
        super.postInit();

        stockItemField.addValueChangeListener(e -> stockItemChanged());

        countedQuantityField.addValueChangeListener(e -> quantityChanged());

    }


    private void quantityChanged() {
        getItem().setAdjustedValue(
                (getItem().getCountedQuantity().subtract(getItem().getCurrentQuantity()))
                        .multiply(getItem().getCurrentValue()));
    }

    private void stockItemChanged() {

        StockCountItem mySCI = getItem();

        BigDecimal currentCost = stockItemService.getPointInTimeCost(
                mySCI.getStockItem().getId(),
                mySCI.getStockCount().getDocumentOn());

        BigDecimal currentQuantity = stockItemService.getPointInTimeQuantity(
                mySCI.getStockItem().getId(),
                mySCI.getStockCount().getDocumentOn());

        getItem().setCurrentValue(currentCost);
        getItem().setCurrentQuantity(currentQuantity);

    }

}