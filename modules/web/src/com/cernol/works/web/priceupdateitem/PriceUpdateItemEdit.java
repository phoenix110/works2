package com.cernol.works.web.priceupdateitem;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.PriceUpdateItem;

import java.math.BigDecimal;

public class PriceUpdateItemEdit extends AbstractEditor<PriceUpdateItem> {

    @Override
    protected void postInit() {
        super.postInit();

        getItem().setPrice(BigDecimal.ZERO);
    }
}