package com.cernol.works.web.salesorderrawmaterial;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.SalesOrderRawMaterial;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Named;

public class SalesOrderRawMaterialEdit extends AbstractEditor<SalesOrderRawMaterial> {

    @Named("fieldGroup.rawMaterial")
    private PickerField rawMaterialField;

    @Override
    protected void postInit() {
        super.postInit();

        rawMaterialField.addValueChangeListener(e -> rawMaterialsChanged());
    }

    private void rawMaterialsChanged() {
        getItem().setUnitPrice(getItem().getRawMaterial().getCost());
    }
}