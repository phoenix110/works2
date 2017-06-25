package com.cernol.works.web.decantingordertarget;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.DecantingOrderTarget;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Named;

public class DecantingOrderTargetEdit extends AbstractEditor<DecantingOrderTarget> {

    @Named("fieldGroup.container")
    private PickerField containerField;

    @Override
    protected void postInit() {
        super.postInit();

        containerField.addValueChangeListener(e -> containerChanged());
    }

    private void containerChanged() {
        getItem().setUnitCost(getItem().getContainer().getCostPerUnit());
    }
}