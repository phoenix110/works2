package com.cernol.works.web.priceupdateitem;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.PriceUpdateItem;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

public class PriceUpdateItemEdit extends AbstractEditor<PriceUpdateItem> {

   @Inject
    StockItemService stockItemService;

    @Inject
    private TextField currentPriceTextField;

    @Named("fieldGroup.stockItem")
    private LookupPickerField stockItemField;

    @Override
    protected void postInit() {
        super.postInit();

        stockItemField.addValueChangeListener(e -> itemChanged());


    }

    @Override
    protected void initNewItem(PriceUpdateItem item) {
        super.initNewItem(item);

        item.setPrice(BigDecimal.ZERO);
    }

    private void itemChanged() {
        currentPriceTextField.setValue(
                stockItemService.getPointInTimeCost(
                        getItem().getStockItem().getId(),getItem().getPriceUpdate().getDocumentOn()));
    }
}