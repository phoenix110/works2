package com.cernol.works.web.priceupdate;

import com.cernol.works.entity.DocumentStatus;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.PriceUpdate;

import javax.inject.Inject;
import java.sql.Date;

public class PriceUpdateEdit extends AbstractEditor<PriceUpdate> {

    @Inject
    ToolsService toolsService;

    @Override
    protected void initNewItem(PriceUpdate item) {
        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setCurrentStatus(DocumentStatus.New);

    }
}