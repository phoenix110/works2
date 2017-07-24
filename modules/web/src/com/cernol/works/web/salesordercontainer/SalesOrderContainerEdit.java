package com.cernol.works.web.salesordercontainer;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.SalesOrderContainer;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Inject;
import javax.inject.Named;

public class SalesOrderContainerEdit extends AbstractEditor<SalesOrderContainer> {

    @Inject
    private StockItemService stockItemService;

    @Named("fieldGroup.container")
    protected PickerField container;

    @Override
    protected void postInit() {
        super.postInit();

        container.addValueChangeListener(e -> containerChanged());
    }

    private void containerChanged() {

        getItem().setUnitPrice(stockItemService.getPointInTimeCost(
                getItem().getContainer().getId(),
                getItem().getSalesOrder().getDocumentOn()));
    }
}