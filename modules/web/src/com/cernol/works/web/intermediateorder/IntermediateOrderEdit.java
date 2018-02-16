package com.cernol.works.web.intermediateorder;

import com.cernol.works.entity.*;
import com.cernol.works.service.StockItemService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class IntermediateOrderEdit extends AbstractEditor<IntermediateOrder> {
    private Logger log = LoggerFactory.getLogger(IntermediateOrderEdit.class);

    @Inject
    private TimeSource timeSource;

    @Inject
    private DataManager dataManager;

    @Inject
    private Metadata metadata;

    @Inject
    private StockItemService stockItemService;

    @Inject
    private CollectionDatasource<IntermediateOrderIngredient, UUID> intermediateOrderIngredientsDs;

    @Inject
    private CollectionDatasource<ProblemList, UUID> problemListsDs;

    @Inject
    private Button windowCommit;

    @Inject
    private Button windowCommitAndClose;

    @Named("fieldGroup.product")
    private PickerField productField;

    @Named("fieldGroup.volume")
    private TextField volumeField;

    @Named("fieldGroup.mass")
    private TextField massField;

    @Named("fieldGroup.currentStatus")
    private LookupField currentStatusField;

    @Inject
    private Button reportButton;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        reportButton.setAction(new EditorPrintFormAction("report", this, null));
    }

    @Override
    protected void initNewItem(IntermediateOrder item) {
        log.info("initNewItem()");
        super.initNewItem(item);

        item.setDocumentOn(timeSource.currentTimestamp());
        item.setDescription("Intermediate Order - No product");
        item.setUnit(Unit.Litre);
        item.setCurrentStatus(DocumentStatus.New);
        item.setContainerCost(BigDecimal.ZERO);
        item.setRawMaterialCost(BigDecimal.ZERO);
        item.setOverheadCost(BigDecimal.ZERO);
        item.setLableCost(BigDecimal.ZERO);
        item.setBatchQuantity(0);
    }

    @Override
    protected void postInit() {
        log.info("postInit");
        super.postInit();

        productField.addValueChangeListener(e -> productChanged());

        volumeField.addValueChangeListener(e -> volumeChanged());

        massField.addValueChangeListener(e -> massChanged());

        currentStatusField.addValueChangeListener(e -> statusChanged());

    }

    private void statusChanged() {
        log.info("statusChanged()");

        if (getItem().getCurrentStatus() == DocumentStatus.Cancelled) {

            for (IntermediateOrderIngredient orderIngredient : intermediateOrderIngredientsDs.getItems()) {
                orderIngredient.setMass(BigDecimal.ZERO);
            }

            intermediateOrderIngredientsDs.refresh();

            if (getItem().getProduct() != null) {
                getItem().setDescription("Cancelled: " + getItem().getProduct().getCode());
            }
            else {
                getItem().setDescription("Cancelled: No product");
            }

        } else {
            if (getItem().getProduct() != null) {

                getItem().setDescription(getItem().getProduct().getCode());
            } else
            {
                getItem().setDescription("Intermediate Order - No product");
            }
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

    private void volumeChanged() {
        log.info("volumeChanged()");

        if (getItem().getProduct() != null) {
            getItem().setMass(calculateMass(getItem().getVolume()));
            getItem().setBatchQuantity(calculateBatches(getItem().getVolume()));
        }

    }

    private void massChanged() {
        log.info("massChanged()");

        resetIngredientQuantities(getItem().getMass());

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

        removeAllIngredients();

        if (getItem().getProduct() != null) {

            LoadContext<Product> loadContext = LoadContext.create(Product.class)
                    .setId(getItem().getProduct().getId())
                    .setView("product-view");

            Product myProduct = dataManager.load(loadContext);

            for (Formula formula : myProduct != null ? myProduct.getFormula() : null) {

                IntermediateOrderIngredient orderIngredient = metadata.create(IntermediateOrderIngredient.class);

                orderIngredient.setIntermediateOrder(getItem());
                orderIngredient.setPartsPer100(formula.getPartsPer100());
                orderIngredient.setSequenceNo(formula.getSequenceNo());
                orderIngredient.setRawMaterial(formula.getRawMaterial());
                orderIngredient.setMass(getItem().getMass()
                        .multiply(formula.getPartsPer100())
                        .divide(BigDecimal.valueOf(100.0), 4, BigDecimal.ROUND_HALF_DOWN));

                intermediateOrderIngredientsDs.addItem(orderIngredient);
                //               worksOrderIngredientsDs.includeItem(orderIngredient);

            }
        }

    }

    private void removeAllIngredients() {
        for (IntermediateOrderIngredient orderIngredient : new ArrayList<>(intermediateOrderIngredientsDs.getItems())) {
            intermediateOrderIngredientsDs.removeItem(orderIngredient);
        }
    }

    private void resetIngredientQuantities(BigDecimal mass) {
        log.info("resetIngredientQuantities()");

        windowCommit.setEnabled(true);
        windowCommitAndClose.setEnabled(true);

        for (ProblemList problemList : new ArrayList<>(problemListsDs.getItems())) {
            problemListsDs.removeItem(problemList);
        }

        for (IntermediateOrderIngredient orderIngredient : new ArrayList<>(intermediateOrderIngredientsDs.getItems())) {
            orderIngredient.setMass(mass.multiply(orderIngredient.getPartsPer100())
                    .divide(BigDecimal.valueOf(100.0), 4, BigDecimal.ROUND_HALF_DOWN));

            BigDecimal onhandQuantity = stockItemService.
                    getPointInTimeQuantity(
                            orderIngredient.getRawMaterial().getId(),
                            getItem().getDocumentOn());

            if (onhandQuantity.compareTo(orderIngredient.getMass()) < 0) {
                ProblemList problem = new ProblemList();
                problem.setParent(getItem().getId());
                problem.setDescription("Not enough stock: " +
                        orderIngredient.getRawMaterial().getInstanceName() +
                        "(" +
                        onhandQuantity +
                        " left)");

                problemListsDs.addItem(problem);

                windowCommit.setEnabled(false);
                windowCommitAndClose.setEnabled(false);

            }
        }
    }
}