package com.cernol.works.web.worksorderpacking;

import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.WorksOrderPacking;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.ValidationErrors;

import javax.inject.Inject;
import javax.inject.Named;

public class WorksOrderPackingEdit extends AbstractEditor<WorksOrderPacking> {


    @Inject
    private StockItemService stockItemService;

    @Inject
    private TimeSource timeSource;

    @Named("fieldGroup.container")
    protected PickerField container;


    @Override
    protected void postInit() {
        super.postInit();


        container.addValueChangeListener(e -> containerChanged());


    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        super.postValidate(errors);
        Integer stockOnHand = stockItemService.getPointInTimeQuantity(
                getItem().getContainer().getId(),
                getItem().getWorksOrder().getDocumentOn())
                .intValue();

        if (getItem().getQuantity() > stockOnHand) {
            errors.add("Not enough containers in stock (" + stockOnHand.toString()+ " left)");
        }
    }

    private void containerChanged() {

        getItem().setUnitCost(
                stockItemService.getPointInTimeCost(
                        getItem().getContainer().getId(),
                        getItem().getWorksOrder().getDocumentOn()));
    }

}