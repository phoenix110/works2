package com.cernol.works.web.salesorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class SalesOrderEdit extends AbstractEditor<SalesOrder> {

    @Inject
    ToolsService toolsService;


    @Inject
    private CollectionDatasource<SalesOrderContainer, UUID> salesOrderContainersDs;

    @Inject
    private CollectionDatasource<SalesOrderRawMaterial, UUID> salesOrderRawMaterialsDs;

    @Named("fieldGroup.invoiceNo")
    protected TextField invoiceField;

    @Override
    protected void initNewItem(SalesOrder item) {
        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setDescription("Sales - No Invoice");
        item.setUnit(Unit.Kilogram);
        item.setCurrentStatus(DocumentStatus.New);
    }

    @Override
    protected void postInit() {
        super.postInit();

        invoiceField.addValueChangeListener(e -> invoiceChanged());

        salesOrderContainersDs.addCollectionChangeListener(e -> containersChanged());

        salesOrderRawMaterialsDs.addCollectionChangeListener(e -> rawMaterialsChanged());
    }

    private void invoiceChanged() {
        getItem().setDescription("Sales - " + getItem().getInvoiceNo());
    }

    private void containersChanged() {

        BigDecimal containerCost = BigDecimal.ZERO;

        for (SalesOrderContainer line : salesOrderContainersDs.getItems()) {
            containerCost = containerCost.add(line.getLinePrice());
        }

        getItem().setContainerCost(containerCost);
    }

    private void rawMaterialsChanged() {

        BigDecimal rawMaterialCost = BigDecimal.ZERO;

        for (SalesOrderRawMaterial line : salesOrderRawMaterialsDs.getItems()) {
            rawMaterialCost = rawMaterialCost.add(line.getLinePrice());
        }

        getItem().setRawMaterialCost(rawMaterialCost);

    }
}