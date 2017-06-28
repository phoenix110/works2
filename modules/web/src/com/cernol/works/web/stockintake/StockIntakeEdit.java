package com.cernol.works.web.stockintake;

import com.cernol.works.entity.DocumentStatus;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.StockIntake;

import javax.inject.Inject;
import java.util.Date;

public class StockIntakeEdit extends AbstractEditor<StockIntake> {

    @Inject
    private ToolsService toolsService;

    @Override
    protected void initNewItem(StockIntake item) {
        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setCurrentStatus(DocumentStatus.New);
    }
}