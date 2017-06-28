package com.cernol.works.web.worksorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class WorksOrderEdit extends AbstractEditor<WorksOrder> {

    @Inject
    ToolsService toolsService;

    @Inject
    private WorksConfig worksConfig;

    @Inject
    private DataManager dataManager;

    @Inject
    private CollectionDatasource<WorksOrderIngredient, UUID> worksOrderIngredientsDs;

    @Inject
    private CollectionDatasource<WorksOrderPacking, UUID> worksOrderPackingsDs;

    @Inject
    private Metadata metadata;

    @Named("fieldGroup.product")
    protected PickerField productPicker;


    @Override
    protected void initNewItem(WorksOrder item) {
        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setDescription("Works Order - No product");
        item.setManufacturingKey(ManufacturingKey.Orders);
        item.setUnit(Unit.Litre);
        item.setCurrentStatus(DocumentStatus.New);
    }

    @Override
    protected void postInit() {
        super.postInit();

        productPicker.addValueChangeListener(e -> productChanged());

        worksOrderPackingsDs.addCollectionChangeListener(e -> packingChanged());
    }

    private void packingChanged() {

        BigDecimal volume = BigDecimal.ZERO;

        BigDecimal containerCost = BigDecimal.ZERO;

        for (WorksOrderPacking line : worksOrderPackingsDs.getItems()) {
            volume = volume.add(line.getLineCapacity());

            containerCost = containerCost.add(line.getLineCost());
        }

        getItem().setVolume(volume);
        getItem().setMass(calculateMass(volume));
        getItem().setBatchQuantity(calculateBatches(volume));
        getItem().setContainerCost(containerCost);
        resetIngredients();
        getItem().setOverheadCost(calculateOverhead());
    }

    private void productChanged() {

        if (getItem().getProduct() != null) {

            getItem().setDescription(getItem().getProduct().getCode());
            getItem().setMass(calculateMass(getItem().getVolume()));
            getItem().setUnit(getItem().getProduct().getUnit());
            resetIngredients();
            getItem().setOverheadCost(calculateOverhead());
        }
    }

    private BigDecimal calculateOverhead() {
        BigDecimal overheadCost = BigDecimal.ZERO;

        if (getItem().getProduct().getApplyOverhead()) {
            overheadCost = getItem().getContainerCost()
                    .add(getItem().getRawMaterialCost())
                    .add(getItem().getLableCost())
                    .multiply(BigDecimal.valueOf(worksConfig.getOrderOverhead(),0))
                    .divide(BigDecimal.valueOf(100),2);
        }

        return overheadCost;
    }

    private BigDecimal calculateMass(BigDecimal volume) {
        BigDecimal mass = BigDecimal.ZERO;

        mass = volume.multiply(getItem().getProduct().getSpecificGravity());

        return mass;
    }

    private Integer calculateBatches(BigDecimal volume) {

        Integer batches = 0;
        BigDecimal bdBatches = BigDecimal.ZERO;

        if (getItem().getMixer() == null) {
            getItem().setBatchQuantity(0);
        } else {
            if (volume.compareTo(BigDecimal.valueOf(getItem().getMixer().getMaxLoad())) < 0) {
                batches = 1;
            } else {
                bdBatches = volume.divide(BigDecimal.valueOf(getItem().getMixer().getMaxLoad()),BigDecimal.ROUND_DOWN);
                batches = bdBatches.intValue()+1;
            }

        }

        return batches;
    }

    public void resetIngredients() {

        BigDecimal ingredientCost = BigDecimal.ZERO;

        removeAllIngredients();

        if (getItem().getProduct() != null) {

            LoadContext<Product> loadContext = LoadContext.create(Product.class)
                    .setId(getItem().getProduct().getId())
                    .setView("product-view");

            Product myProduct = dataManager.load(loadContext);

            for (Formula formula : myProduct.getFormula()) {

                WorksOrderIngredient orderIngredient = metadata.create(WorksOrderIngredient.class);

                BigDecimal partsPer100 = formula.getPartsPer100();

                orderIngredient.setWorksOrder(getItem());
                orderIngredient.setSequenceNo(formula.getSequenceNo());
                orderIngredient.setRawMaterial(formula.getRawMaterial());
                orderIngredient.setMass(getItem().getMass()
                        .multiply(partsPer100)
                        .divide(BigDecimal.valueOf(100.0), 4, BigDecimal.ROUND_HALF_DOWN));
                orderIngredient.setKgCost(formula.getRawMaterial().getCost());

                worksOrderIngredientsDs.addItem(orderIngredient);

                ingredientCost = ingredientCost.add(orderIngredient.getLineCost());
            }
        }

        getItem().setRawMaterialCost(ingredientCost);
    }

    private void removeAllIngredients() {
        for (WorksOrderIngredient worksOrderIngredient : new ArrayList<>(worksOrderIngredientsDs.getItems())) {
            worksOrderIngredientsDs.removeItem(worksOrderIngredient);
        }
    }
}