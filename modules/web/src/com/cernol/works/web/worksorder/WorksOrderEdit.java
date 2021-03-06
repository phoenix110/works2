package com.cernol.works.web.worksorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WorksOrderEdit extends AbstractEditor<WorksOrder> {

    private Logger log = LoggerFactory.getLogger(WorksOrderEdit.class);

    @Inject
    private StockItemService stockItemService;

    @Inject
    private WorksConfig worksConfig;

    @Inject
    private DataManager dataManager;

    @Inject
    private Datasource<WorksOrder> worksOrderDs;

    @Inject
    private CollectionDatasource<WorksOrderIngredient, UUID> worksOrderIngredientsDs;

    @Inject
    private CollectionDatasource<WorksOrderPacking, UUID> worksOrderPackingsDs;

    @Inject
    private CollectionDatasource<WorksOrderLable, UUID> worksOrderLablesDs;

    @Inject
    private CollectionDatasource<ProblemList, UUID> problemListsDs;

    @Inject
    private CollectionDatasource<WorksOrderShipper, UUID> worksOrderShippersDs;

    @Inject
    private Metadata metadata;

    @Inject
    private TimeSource timeSource;

    @Inject
    private Button instructionReportBtn;

    @Inject
    private Button windowCommit;

    @Inject
    private Button windowCommitAndClose;

    @Named("fieldGroup.product")
    protected PickerField productPicker;

    @Named("fieldGroup.mass")
    private TextField massField;

    @Named("fieldGroup.volume")
    private TextField volumeField;

    @Named("fieldGroup.currentStatus")
    private LookupField currentStatusField;

    @Named("fieldGroup.containerCost")
    private TextField containerCostField;

    @Named("fieldGroup.rawMaterialCost")
    private TextField rawMaterialCostField;


    @Override
    public void init(Map<String, Object> params) {
        log.info("init()");

        super.init(params);

        instructionReportBtn.setAction(new EditorPrintFormAction(this, null));

    }

    @Override
    protected void initNewItem(WorksOrder item) {

        log.info("initNewItem()");

        super.initNewItem(item);

        item.setDocumentOn(timeSource.currentTimestamp());
        item.setDescription("Works Order - No product");
        item.setManufacturingKey(ManufacturingKey.Orders);
        item.setUnit(Unit.Litre);
        item.setCurrentStatus(DocumentStatus.New);
    }

    @Override
    protected void postInit() {
        log.info("postInit()");
        super.postInit();

        volumeField.addValueChangeListener(e -> volumeChanged());

        massField.addValueChangeListener(e -> massChanged());

        productPicker.addValueChangeListener(e -> productChanged());

        containerCostField.addValueChangeListener(e -> containerCostChanged());

        rawMaterialCostField.addValueChangeListener(e -> rawMaterialCostChanged());

        currentStatusField.addValueChangeListener(e -> statusChanged());

        worksOrderPackingsDs.addCollectionChangeListener(e -> packingChanged());

        //worksOrderIngredientsDs.addCollectionChangeListener(e -> ingredientsChanged());

        worksOrderLablesDs.addCollectionChangeListener(e -> labelsChanged());

        worksOrderShippersDs.addCollectionChangeListener(e -> shippersChanged());

    }

    private void packingChanged() {
        log.info("packingChanged()");

        BigDecimal volume = BigDecimal.ZERO;
        BigDecimal containerCost = BigDecimal.ZERO;

        for (WorksOrderPacking line : worksOrderPackingsDs.getItems()) {
            volume = volume.add(line.getLineCapacity());

            containerCost = containerCost.add(line.getLineCost());
        }

        getItem().setVolume(volume);
        getItem().setContainerCost(containerCost);

        if (getItem().getProduct() != null) {
            getItem().setMass(calculateMass(volume));

        }
    }

    private void ingredientsChanged() {

        log.info("ingredientsChanged()");

        BigDecimal rawMaterialCost = BigDecimal.ZERO;

        for (WorksOrderIngredient line : worksOrderIngredientsDs.getItems()) {
            rawMaterialCost = rawMaterialCost.add(line.getLineCost());
        }

        getItem().setRawMaterialCost(rawMaterialCost);
    }

    private void volumeChanged() {
        log.info("volumeChanged()");

        if (getItem().getProduct() != null) {
            getItem().setMass(calculateMass(getItem().getVolume()));
            getItem().setBatchQuantity(calculateBatches(getItem().getVolume()));
            resetLabels();
            resetShippers();
        }

    }

    private void massChanged() {
        log.info("massChanged()");

        resetIngredientQuantities(getItem().getMass());
        ingredientsChanged();
    }

    private void containerCostChanged() {
        log.info("containerCostChanged()");
        getItem().setOverheadCost(calculateOverhead());
    }

    private void rawMaterialCostChanged() {
        log.info("rawMaterialCostChanged()");
        getItem().setOverheadCost(calculateOverhead());
    }

    private void statusChanged() {

        log.info("statusChanged()");

        if (getItem().getCurrentStatus() == DocumentStatus.Cancelled) {

            for (WorksOrderPacking line : worksOrderPackingsDs.getItems()) {
                line.setQuantity(0);
            }
            worksOrderPackingsDs.refresh();

            if (getItem().getProduct() != null) {
                getItem().setDescription("Cancelled: " + getItem().getProduct().getCode());
            } else {
                getItem().setDescription("Cancelled: No product");

            }

        } else {
            if (getItem().getProduct() != null) {

                getItem().setDescription(getItem().getProduct().getCode());
            } else {
                getItem().setDescription("Works Order - No product");
            }
            packingChanged();

        }

    }

    private void productChanged() {
        log.info("productChanged()");
        if (getItem().getProduct() != null) {

            getItem().setDescription(getItem().getProduct().getCode());
            getItem().setUnit(getItem().getProduct().getUnit());
            resetIngredients();
            resetIngredientQuantities(getItem().getMass());
        }
    }

    private BigDecimal calculateOverhead() {
        log.info("calculateOverhead()");
        BigDecimal overheadCost = BigDecimal.ZERO;

        if (getItem().getProduct().getApplyOverhead()) {

            BigDecimal rawMaterialOverhead = getItem().getRawMaterialCost()
                    .multiply(BigDecimal.valueOf(worksConfig.getRawMaterialOverheadPercentage()))
                    .divide(BigDecimal.valueOf(100), 2);

            BigDecimal containerOverhead = getItem().getContainerCost()
                    .multiply(BigDecimal.valueOf(worksConfig.getContainerOverheadPercentage()))
                    .divide(BigDecimal.valueOf(100), 2);

            BigDecimal labelOverhead = getItem().getLableCost()
                    .multiply(BigDecimal.valueOf(worksConfig.getLabelOverheadPercentage()))
                    .divide(BigDecimal.valueOf(100), 2);

            BigDecimal packingOverhead = getItem().getPackingCost()
                    .multiply(BigDecimal.valueOf(worksConfig.getPackingOverheadPercentage()))
                    .divide(BigDecimal.valueOf(100), 2);

            overheadCost = rawMaterialOverhead
                    .add(containerOverhead)
                    .add(labelOverhead)
                    .add(packingOverhead)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return overheadCost;
    }

    private BigDecimal calculateMass(BigDecimal volume) {
        log.info("calculateMass()");

        return volume.multiply(getItem().getProduct().getSpecificGravity());
    }

    private Integer calculateBatches(BigDecimal volume) {
        log.info("calculateBatches()");
        Integer batches = 0;
        BigDecimal bdBatches = BigDecimal.ZERO;

        if (getItem().getMixer() == null) {
            getItem().setBatchQuantity(0);
        } else {
            if (volume.compareTo(BigDecimal.valueOf(getItem().getMixer().getMaxLoad())) < 0) {
                batches = 1;
            } else {
                bdBatches = volume.divide(BigDecimal.valueOf(getItem().getMixer().getMaxLoad()), BigDecimal.ROUND_DOWN);
                batches = bdBatches.intValue() + 1;
            }

        }

        return batches;
    }

    public void resetIngredients() {
        log.info("resetIngredients()");
        BigDecimal ingredientCost = BigDecimal.ZERO;

        removeAllIngredients();

        if (getItem().getProduct() != null) {

            LoadContext<Product> loadContext = LoadContext.create(Product.class)
                    .setId(getItem().getProduct().getId())
                    .setView("product-view");

            Product myProduct = dataManager.load(loadContext);

            for (Formula formula : myProduct != null ? myProduct.getFormula() : null) {

                WorksOrderIngredient orderIngredient = metadata.create(WorksOrderIngredient.class);

                orderIngredient.setWorksOrder(getItem());
                orderIngredient.setPartsPer100(formula.getPartsPer100());
                orderIngredient.setSequenceNo(formula.getSequenceNo());
                orderIngredient.setRawMaterial(formula.getRawMaterial());
                orderIngredient.setMass(getItem().getMass()
                        .multiply(formula.getPartsPer100())
                        .divide(BigDecimal.valueOf(100.0), 4, BigDecimal.ROUND_HALF_DOWN));


                BigDecimal currentCost = stockItemService.getPointInTimeCost(
                        orderIngredient.getRawMaterial().getId(),
                        getItem().getDocumentOn());

                orderIngredient.setKgCost(currentCost);


                worksOrderIngredientsDs.addItem(orderIngredient);
                //               worksOrderIngredientsDs.includeItem(orderIngredient);

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

    @Override
    protected void postValidate(ValidationErrors errors) {
        super.postValidate(errors);

        if (problemListsDs.getItems().size() > 0) {
            errors.add("Insufficient stock levels.");
        }

    }

    private void resetIngredientQuantities(BigDecimal mass) {
        log.info("resetIngredientQuantities()");

        windowCommit.setEnabled(true);
        windowCommitAndClose.setEnabled(true);


        for (ProblemList problemList : new ArrayList<>(problemListsDs.getItems())) {
            problemListsDs.removeItem(problemList);
        }

        for (WorksOrderIngredient worksOrderIngredient : new ArrayList<>(worksOrderIngredientsDs.getItems())) {
            worksOrderIngredient.setMass(mass.
                    multiply(worksOrderIngredient.getPartsPer100()).
                    divide(BigDecimal.valueOf(100.0), 4, BigDecimal.ROUND_HALF_DOWN));

            BigDecimal onhandQuantity = stockItemService.
                    getPointInTimeQuantity(worksOrderIngredient.getRawMaterial().getId(), getItem().getDocumentOn());

            if (onhandQuantity.compareTo(worksOrderIngredient.getMass()) < 0) {
                ProblemList problem = new ProblemList();
                problem.setParent(getItem().getId());
                problem.setDescription("Not enough stock: " +
                        worksOrderIngredient.getRawMaterial().getInstanceName() +
                        "(" +
                        onhandQuantity +
                        " left)");

                problemListsDs.addItem(problem);


                windowCommit.setEnabled(false);
                windowCommitAndClose.setEnabled(false);

            }


            worksOrderIngredientsDs.updateItem(worksOrderIngredient);

        }
    }

    private void resetShippers() {
        log.info("resetShippers");

        removeAllShippers();

        for (WorksOrderPacking worksOrderPacking : new ArrayList<>(worksOrderPackingsDs.getItems())) {
            com.cernol.works.entity.Container container = worksOrderPacking.getContainer();

            if ((container.getUnitsPerShipper() != null) && (container.getUnitsPerShipper() > 0)) {
                Packing packing = container.getPacking();

                if (packing != null) {
                    WorksOrderShipper worksOrderShipper = metadata.create(WorksOrderShipper.class);
                    worksOrderShipper.setWorksOrder(getItem());
                    worksOrderShipper.setPacking(packing);
                    worksOrderShipper.setUnitCost(stockItemService.getPointInTimeCost(
                            packing.getId(),
                            getItem().getDocumentOn()));
                    BigDecimal containerQuantity = BigDecimal.valueOf(worksOrderPacking.getQuantity());
                    BigDecimal containersPerShipper = BigDecimal.valueOf(container.getUnitsPerShipper());
                    worksOrderShipper.setQuantity(containerQuantity.divide(containersPerShipper,2,BigDecimal.ROUND_HALF_UP));

                    worksOrderShippersDs.addItem(worksOrderShipper);

                }

            }
        }


    }

    private void removeAllShippers() {

        for (WorksOrderShipper worksOrderShipper : new ArrayList<>(worksOrderShippersDs.getItems())) {
            worksOrderShippersDs.removeItem(worksOrderShipper);
        }

    }

    public void shippersChanged() {
        BigDecimal shipperCost = BigDecimal.ZERO;
        for (WorksOrderShipper worksOrderShipper : worksOrderShippersDs.getItems()) {
            shipperCost = shipperCost.add(worksOrderShipper.getLineCost());

        }
        getItem().setPackingCost(shipperCost);
        getItem().setOverheadCost(calculateOverhead());
    }

    private void resetLabels() {
        log.info("resetLabels()");


        List<WorksOrderPacking> worksOrderPackingList;
        List<ProductContainer> productContainerList;

        removeAllLabels();

        LoadContext<Product> loadContext = LoadContext.create(Product.class)
                .setId(getItem().getProduct().getId())
                .setView("product-view");

        Product myProduct = dataManager.load(loadContext);

        if (myProduct != null) {
            worksOrderPackingList = getItem().getWorksOrderPackings();
            if (worksOrderPackingList.size() > 0) {

                productContainerList = myProduct.getContainers();
                for (WorksOrderPacking worksOrderPacking : worksOrderPackingList) {
                    for (ProductContainer productContainer : productContainerList) {
                        if (productContainer.getContainer().getId().equals(worksOrderPacking.getContainer().getId())) {
                            if (productContainer.getProductLabel() != null) {
                                Boolean newItem = Boolean.TRUE;
                                for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
                                    if (worksOrderLable.getLable() == productContainer.getProductLabel()) {
                                        worksOrderLable.setQuantity(worksOrderLable.getQuantity() +
                                                worksOrderPacking.getQuantity());
                                        worksOrderLable.setUnitCost(
                                                stockItemService.getPointInTimeCost(
                                                        productContainer.getProductLabel().getId(),
                                                        getItem().getDocumentOn()));
                                        newItem = Boolean.FALSE;
                                    }
                                }
                                if (newItem) {
                                    WorksOrderLable worksOrderLable = metadata.create(WorksOrderLable.class);
                                    worksOrderLable.setWorksOrder(getItem());
                                    worksOrderLable.setLable(productContainer.getProductLabel());
                                    worksOrderLable.setQuantity(worksOrderPacking.getQuantity());
                                    worksOrderLable.setUnitCost(
                                            stockItemService.getPointInTimeCost(
                                                    productContainer.getProductLabel().getId(),
                                                    getItem().getDocumentOn()));
                                    worksOrderLablesDs.addItem(worksOrderLable);
                                }
                            }

                            if (productContainer.getCorrosiveLabel() != null) {
                                Boolean newItem = Boolean.TRUE;
                                for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
                                    if (worksOrderLable.getLable() == productContainer.getCorrosiveLabel()) {
                                        worksOrderLable.setQuantity(worksOrderLable.getQuantity() +
                                                worksOrderPacking.getQuantity());
                                        worksOrderLable.setUnitCost(
                                                stockItemService.getPointInTimeCost(
                                                        productContainer.getCorrosiveLabel().getId(),
                                                        getItem().getDocumentOn()));
                                        newItem = Boolean.FALSE;
                                    }
                                }
                                if (newItem) {
                                    WorksOrderLable worksOrderLable = metadata.create(WorksOrderLable.class);
                                    worksOrderLable.setWorksOrder(getItem());
                                    worksOrderLable.setLable(productContainer.getCorrosiveLabel());
                                    worksOrderLable.setQuantity(worksOrderPacking.getQuantity());
                                    worksOrderLable.setUnitCost(
                                            stockItemService.getPointInTimeCost(
                                                    productContainer.getCorrosiveLabel().getId(),
                                                    getItem().getDocumentOn()));
                                    worksOrderLablesDs.addItem(worksOrderLable);
                                }
                            }

                            if (productContainer.getPoisonousLabel() != null) {
                                Boolean newItem = Boolean.TRUE;
                                for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
                                    if (worksOrderLable.getLable() == productContainer.getPoisonousLabel()) {
                                        worksOrderLable.setQuantity(worksOrderLable.getQuantity() +
                                                worksOrderPacking.getQuantity());
                                        worksOrderLable.setUnitCost(
                                                stockItemService.getPointInTimeCost(
                                                        productContainer.getPoisonousLabel().getId(),
                                                        getItem().getDocumentOn()));
                                        newItem = Boolean.FALSE;
                                    }
                                }
                                if (newItem) {
                                    WorksOrderLable worksOrderLable = metadata.create(WorksOrderLable.class);
                                    worksOrderLable.setWorksOrder(getItem());
                                    worksOrderLable.setLable(productContainer.getPoisonousLabel());
                                    worksOrderLable.setQuantity(worksOrderPacking.getQuantity());
                                    worksOrderLable.setUnitCost(
                                            stockItemService.getPointInTimeCost(
                                                    productContainer.getPoisonousLabel().getId(),
                                                    getItem().getDocumentOn()));
                                    worksOrderLablesDs.addItem(worksOrderLable);
                                }
                            }

                            if (productContainer.getFlammableLabel() != null) {
                                Boolean newItem = Boolean.TRUE;
                                for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
                                    if (worksOrderLable.getLable() == productContainer.getFlammableLabel()) {
                                        worksOrderLable.setQuantity(worksOrderLable.getQuantity() +
                                                worksOrderPacking.getQuantity());
                                        worksOrderLable.setUnitCost(
                                                stockItemService.getPointInTimeCost(
                                                        productContainer.getFlammableLabel().getId(),
                                                        getItem().getDocumentOn()));
                                        newItem = Boolean.FALSE;
                                    }
                                }
                                if (newItem) {
                                    WorksOrderLable worksOrderLable = metadata.create(WorksOrderLable.class);
                                    worksOrderLable.setWorksOrder(getItem());
                                    worksOrderLable.setLable(productContainer.getFlammableLabel());
                                    worksOrderLable.setQuantity(worksOrderPacking.getQuantity());
                                    worksOrderLable.setUnitCost(
                                            stockItemService.getPointInTimeCost(
                                                    productContainer.getFlammableLabel().getId(),
                                                    getItem().getDocumentOn()));
                                    worksOrderLablesDs.addItem(worksOrderLable);
                                }
                            }

                            if (productContainer.getKeepAwayLabel() != null) {
                                Boolean newItem = Boolean.TRUE;
                                for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
                                    if (worksOrderLable.getLable() == productContainer.getKeepAwayLabel()) {
                                        worksOrderLable.setQuantity(worksOrderLable.getQuantity() +
                                                worksOrderPacking.getQuantity());
                                        worksOrderLable.setUnitCost(
                                                stockItemService.getPointInTimeCost(
                                                        productContainer.getKeepAwayLabel().getId(),
                                                        getItem().getDocumentOn()));
                                        newItem = Boolean.FALSE;
                                    }
                                }
                                if (newItem) {
                                    WorksOrderLable worksOrderLable = metadata.create(WorksOrderLable.class);
                                    worksOrderLable.setWorksOrder(getItem());
                                    worksOrderLable.setLable(productContainer.getKeepAwayLabel());
                                    worksOrderLable.setQuantity(worksOrderPacking.getQuantity());
                                    worksOrderLable.setUnitCost(
                                            stockItemService.getPointInTimeCost(
                                                    productContainer.getKeepAwayLabel().getId(),
                                                    getItem().getDocumentOn()));
                                    worksOrderLablesDs.addItem(worksOrderLable);
                                }
                            }
                        }
                    }
                }
            }
        }

        worksOrderLablesDs.commit();
    }

    private void removeAllLabels() {
        for (WorksOrderLable worksOrderLabel : new ArrayList<>(worksOrderLablesDs.getItems())) {
            worksOrderLablesDs.removeItem(worksOrderLabel);
        }
    }

    private void labelsChanged() {
        BigDecimal labelCost = BigDecimal.ZERO;
        for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
            labelCost = labelCost.add(worksOrderLable.getLineCost());

        }
        getItem().setLableCost(labelCost);
        getItem().setOverheadCost(calculateOverhead());
    }

}