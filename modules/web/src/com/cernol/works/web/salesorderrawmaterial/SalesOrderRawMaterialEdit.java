package com.cernol.works.web.salesorderrawmaterial;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.SalesOrderRawMaterial;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Inject;
import javax.inject.Named;

public class SalesOrderRawMaterialEdit extends AbstractEditor<SalesOrderRawMaterial> {

    @Inject
    private StockItemService stockItemService;

    @Named("fieldGroup.rawMaterial")
    private PickerField rawMaterialField;

    @Override
    protected void postInit() {
        super.postInit();

        rawMaterialField.addValueChangeListener(e -> rawMaterialsChanged());
    }

    private void rawMaterialsChanged() {
        getItem().setUnitPrice(stockItemService.getPointInTimeCost(
                getItem().getRawMaterial().getId(),
                getItem().getSalesOrder().getDocumentOn()));


    }
}