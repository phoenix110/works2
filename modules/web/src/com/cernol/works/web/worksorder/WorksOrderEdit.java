package com.cernol.works.web.worksorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.StockItemService;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.*;

public class WorksOrderEdit extends AbstractEditor<WorksOrder> {

    private Logger log = LoggerFactory.getLogger(WorksOrderEdit.class);

    @Inject
    ToolsService toolsService;

    @Inject
    private StockItemService stockItemService;

    @Inject
    private WorksConfig worksConfig;

    @Inject
    private DataManager dataManager;

    @Inject
    private CollectionDatasource<WorksOrderIngredient, UUID> worksOrderIngredientsDs;

    @Inject
    private CollectionDatasource<WorksOrderPacking, UUID> worksOrderPackingsDs;

    @Inject
    private CollectionDatasource<WorksOrderLable, UUID> worksOrderLablesDs;

    @Inject
    private CollectionDatasource<ProblemList, UUID> problemListsDs;

    @Inject
    private Metadata metadata;

    @Inject
    private Button instructionReportBtn;

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
    protected boolean postCommit(boolean committed, boolean close) {
        log.debug("postCommit().committed: " + committed);

        return super.postCommit(committed, close);



    }

    @Override
    public void init(Map<String, Object> params) {
        log.debug("init()");

        super.init(params);

        instructionReportBtn.setAction(new EditorPrintFormAction(this, null));

//        worksOrderIngredientsDs.addCollectionChangeListener(e -> packingChanged());

        volumeField.addValueChangeListener(e -> volumeChanged());

        massField.addValueChangeListener(e -> massChanged());


    }



    @Override
    protected void initNewItem(WorksOrder item) {

        log.debug("initNewItem()");

        super.initNewItem(item);

        item.setDocumentOn(Date.from(toolsService.getNow()));
        item.setDescription("Works Order - No product");
        item.setManufacturingKey(ManufacturingKey.Orders);
        item.setUnit(Unit.Litre);
        item.setCurrentStatus(DocumentStatus.New);
    }



    @Override
    protected void postInit() {
        log.debug("postInit()");
        super.postInit();

        productPicker.addValueChangeListener(e -> productChanged());

        containerCostField.addValueChangeListener(e -> containerCostChanged());

        rawMaterialCostField.addValueChangeListener(e -> rawMaterialCostChanged());

        currentStatusField.addValueChangeListener(e -> statusChanged());

        worksOrderPackingsDs.addCollectionChangeListener(e -> packingChanged());

        worksOrderIngredientsDs.addCollectionChangeListener(e -> ingredientsChanged());
    }



    private void packingChanged() {
        log.debug("packingChanged()");
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
//            getItem().setBatchQuantity(calculateBatches(volume));
//            resetIngredients();
//            getItem().setOverheadCost(calculateOverhead());
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

        }

    }

    private void massChanged() {
        log.info("massChanged()");

        resetIngredientQuantities(getItem().getMass());

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
            BigDecimal zeroVal = BigDecimal.ZERO;

            getItem().setMass(zeroVal);
            //getItem().setRawMaterialCost(zeroVal);
            getItem().setContainerCost(zeroVal);
            //getItem().setOverheadCost(zeroVal);
            if (getItem().getProduct() != null) {
                getItem().setDescription("Cancelled: " + getItem().getProduct().getCode());
            }
            else
            {
                getItem().setDescription("Cancelled: No product");

            }

        } else {
            if (getItem().getProduct() != null) {

                getItem().setDescription(getItem().getProduct().getCode());
            } else
            {
                getItem().setDescription("Works Order - No product");
            }
              packingChanged();

        }

    }

    private void productChanged() {
        log.info("productChanged()");
        if (getItem().getProduct() != null) {

            getItem().setDescription(getItem().getProduct().getCode());
            //getItem().setMass(calculateMass(getItem().getVolume()));
            getItem().setUnit(getItem().getProduct().getUnit());
            resetIngredients();
            resetIngredientQuantities(getItem().getMass());
            //getItem().setOverheadCost(calculateOverhead());
        }
    }

    private BigDecimal calculateOverhead() {
        log.info("calculateOverhead()");
        BigDecimal overheadCost = BigDecimal.ZERO;

        if (getItem().getProduct().getApplyOverhead()) {
            overheadCost = (getItem().getContainerCost()
                    .add(getItem().getRawMaterialCost()))
                    .multiply(BigDecimal.valueOf(worksConfig.getOrderOverhead(), 0))

                    .divide(BigDecimal.valueOf(100), 2);
        }

        return overheadCost;
    }

    private BigDecimal calculateMass(BigDecimal volume) {
        log.info("calculateMass()");

        BigDecimal mass = volume.multiply(getItem().getProduct().getSpecificGravity());

        return mass;
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

    private void resetIngredients() {
        log.debug("resetIngredients()");
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

                BigDecimal onhandQuantity = stockItemService.
                        getPointInTimeQuantity(orderIngredient.getRawMaterial().getId(), getItem().getDocumentOn());

                if (onhandQuantity.compareTo(orderIngredient.getMass()) < 0) {
                    ProblemList problem = new ProblemList();
                    problem.setParent(getItem().getId());
                    problem.setDescription("Not enough stock: " +
                            orderIngredient.getRawMaterial().getInstanceName() +
                            "(" +
                            onhandQuantity +
                            " left)");

                    problemListsDs.addItem(problem);
                }

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

    private void resetIngredientQuantities(BigDecimal mass) {
        log.info("resetIngredientQuantities()");

        for (WorksOrderIngredient worksOrderIngredient : new ArrayList<>(worksOrderIngredientsDs.getItems())) {
            worksOrderIngredient.setMass(mass.
                    multiply(worksOrderIngredient.getPartsPer100()).
                    divide(BigDecimal.valueOf(100.0), 4, BigDecimal.ROUND_HALF_DOWN));
            worksOrderIngredientsDs.updateItem(worksOrderIngredient);
        }
    }

    private void resetLabels() {
        log.debug("resetLabels()");
        BigDecimal labelCost = BigDecimal.ZERO;

        List<WorksOrderPacking> worksOrderPackingList;
        List<ProductContainer> productContainerList;
        List<WorksOrderLable> worksOrderLableList;

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

                for (WorksOrderLable worksOrderLable : worksOrderLablesDs.getItems()) {
                    labelCost = labelCost.add(worksOrderLable.getLineCost());

                }
            }
        }

        getItem().setLableCost(labelCost);
    }

    private void removeAllLabels() {
        for (WorksOrderLable worksOrderLabel : new ArrayList<>(worksOrderLablesDs.getItems())) {
            worksOrderLablesDs.removeItem(worksOrderLabel);
        }
    }

    private void resetLabelQuantitities() {

    }
}