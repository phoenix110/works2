package com.cernol.works.web.worksorderpacking;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.WorksOrderPacking;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Inject;
import javax.inject.Named;

public class WorksOrderPackingEdit extends AbstractEditor<WorksOrderPacking> {

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

        getItem().setUnitCost(
                stockItemService.getPointInTimeCost(
                        getItem().getContainer().getId(),
                        getItem().getWorksOrder().getDocumentOn()));
    }
}