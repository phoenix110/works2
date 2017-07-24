package com.cernol.works.web.decantingordertarget;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.DecantingOrderTarget;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Inject;
import javax.inject.Named;

public class DecantingOrderTargetEdit extends AbstractEditor<DecantingOrderTarget> {

    @Inject
    private StockItemService stockItemService;

    @Named("fieldGroup.container")
    private PickerField containerField;

    @Override
    protected void postInit() {
        super.postInit();

        containerField.addValueChangeListener(e -> containerChanged());
    }

    private void containerChanged() {

        getItem().setUnitCost(stockItemService.getPointInTimeCost(
                getItem().getContainer().getId(),
                getItem().getDecantingOrder().getDocumentOn()));
    }
}