package com.cernol.works.web.decantingorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class DecantingOrderEdit extends AbstractEditor<DecantingOrder> {

    @Inject
    private ToolsService toolsService;

    @Inject
    private CollectionDatasource<DecantingOrderSource, UUID> decantingOrderSourcesDs;

    @Inject
    private CollectionDatasource<DecantingOrderTarget, UUID> decantingOrderTargetsDs;

    @Named("fieldGroup.product")
    private PickerField productField;

    @Override
    protected void initNewItem(DecantingOrder item) {
        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setDescription("Decanting - No product");
        item.setUnit(Unit.Litre);
        item.setCurrentStatus(DocumentStatus.New);
    }

    @Override
    protected void postInit() {
        super.postInit();

        productField.addValueChangeListener(e -> productChanged());

        decantingOrderSourcesDs.addCollectionChangeListener(e -> sourceChanged());

        decantingOrderTargetsDs.addCollectionChangeListener(e -> targetsChanged());

    }

    private void productChanged() {
        if (getItem().getProduct() == null) {
            getItem().setDescription("Decanting - No product");
        } else {
            getItem().setDescription("Decanting - " + getItem().getProduct().toString());
        }
    }

    private void sourceChanged() {
        BigDecimal sourceVolume = BigDecimal.ZERO;

        for (DecantingOrderSource line : decantingOrderSourcesDs.getItems()) {
            sourceVolume = sourceVolume.add(line.getLineCapacity());
        }

        getItem().setSourceVolume(sourceVolume);
    }

    private void targetsChanged() {
        BigDecimal targetVolume = BigDecimal.ZERO;
        BigDecimal targetCost = BigDecimal.ZERO;

        for (DecantingOrderTarget line : decantingOrderTargetsDs.getItems()) {
            targetVolume = targetVolume.add(line.getLineCapacity());
            targetCost = targetCost.add(line.getLineCost());
        }

        getItem().setTargetVolume(targetVolume);
        getItem().setContainerCost(targetCost);

    }
}