package com.cernol.works.web.priceupdate;

import com.cernol.works.entity.DocumentStatus;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.PriceUpdate;

public class PriceUpdateEdit extends AbstractEditor<PriceUpdate> {

    @Override
    protected void postInit() {
        super.postInit();

        getItem().setCurrentStatus(DocumentStatus.New);
    }
}