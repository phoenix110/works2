package com.cernol.works.web.stockcount;

import com.cernol.works.entity.DocumentStatus;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cernol.works.entity.StockCount;

import javax.inject.Inject;
import java.sql.Date;

public class StockCountEdit extends AbstractEditor<StockCount> {

    @Inject
    private ToolsService toolsService;

    @Override
    protected void initNewItem(StockCount item) {
        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setCurrentStatus(DocumentStatus.New);
        
    }
}