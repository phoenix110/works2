package com.cernol.works.web.decantingorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class DecantingOrderEdit extends AbstractEditor<DecantingOrder> {

    @Inject
    private ToolsService toolsService;

    @Inject
    WorksConfig worksConfig;

    @Inject
    private CollectionDatasource<DecantingOrderSource, UUID> decantingOrderSourcesDs;

    @Inject
    private CollectionDatasource<DecantingOrderTarget, UUID> decantingOrderTargetsDs;

    @Named("fieldGroup.decantedProduct")
    private PickerField decantedProductField;

    @Inject
    private Button printBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        printBtn.setAction(new EditorPrintFormAction(this,null));
    }

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

        decantedProductField.addValueChangeListener(e -> productChanged());

        decantingOrderSourcesDs.addCollectionChangeListener(e -> sourceChanged());

        decantingOrderTargetsDs.addCollectionChangeListener(e -> targetsChanged());

    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        super.postValidate(errors);

        if (getItem().getSourceVolume().compareTo(getItem().getTargetVolume())!=0) {
            errors.add("Source and target volumes should be equal.");

        }

    }

    private void productChanged() {
        if (getItem().getDecantedProduct() == null) {
            getItem().setDescription("Decanting - No product");
        } else {
            getItem().setDescription("Decanting - " + getItem().getDecantedProduct().getCode());
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
        BigDecimal overheadCost;

        for (DecantingOrderTarget line : decantingOrderTargetsDs.getItems()) {
            targetVolume = targetVolume.add(line.getLineCapacity());
            targetCost = targetCost.add(line.getLineCost());
        }

        if (getItem().getDecantedProduct().getApplyOverhead()) {
            overheadCost = BigDecimal.ZERO;
        }
        else {
            overheadCost = targetCost.multiply(BigDecimal.valueOf(worksConfig.getDecantingOverhead(),0)).
                    divide(BigDecimal.valueOf(100,0));
        }

        getItem().setTargetVolume(targetVolume);
        getItem().setContainerCost(targetCost);
        getItem().setOverheadCost(overheadCost);


    }
}