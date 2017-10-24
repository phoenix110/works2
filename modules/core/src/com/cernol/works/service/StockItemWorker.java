package com.cernol.works.service;

import com.cernol.works.entity.*;
import com.haulmont.cuba.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.Calendar;
;

/**
 * Created by skopp on 28/06/2017.
 */
@Component(StockItemWorker.NAME)
public class StockItemWorker {
    public static final String NAME = "works_StockItemWorker";

    private Logger log = LoggerFactory.getLogger(StockItemWorker.class);

    @Inject
    private ToolsService toolsService;

    @Inject
    private Persistence persistence;

    public BigDecimal getPointInTimeQuantity(final UUID stockItemId, final Date queryDate) {
/*        System.out.println("Entering StockItemWorker.getPointInTimeQuantity() with " +
                stockItemId.toString() +
                " and " +
                queryDate.toString());*/

        //Set earliest possible start date in case we did not count the item yet.

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date beginDate = cal.getTime();

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            TypedQuery<StockCountItem> startQuery = em.createQuery(
                    "select s from works$StockCountItem s " +
                            "where s.stockItem.id = ?1 " +
                            "and s.stockCount.documentOn <= ?2 " +
                            "and s.stockCount.currentStatus = ?3 " +
                            "order by s.stockCount.documentOn desc", StockCountItem.class);

            startQuery.setParameter(1, stockItemId);
            startQuery.setParameter(2, queryDate);
            startQuery.setParameter(3, DocumentStatus.Accepted);
            startQuery.setMaxResults(1);

            StockCountItem startTransaction = startQuery.getFirstResult();

            BigDecimal startQuantity = startTransaction != null ? startTransaction.getCountedQuantity() : BigDecimal.ZERO;

            Date sinceDate = startTransaction != null ? startTransaction.getStockCount().getDocumentOn() : beginDate;

            BigDecimal outQuantity = getOutQuantityRMWO(stockItemId, sinceDate, queryDate)
                    .add(getOutQuanityRMSO(stockItemId, sinceDate, queryDate))
                    .add(getOutQuantityCWO(stockItemId, sinceDate, queryDate))
                    .add(getOutQuantityCDO(stockItemId, sinceDate, queryDate))
                    .add(getOutQuantityCSO(stockItemId, sinceDate, queryDate));

            BigDecimal inQuantity = getInQuantity(stockItemId, sinceDate, queryDate);

/*            System.out.println(" ---> Start:" + startQuantity.toPlainString() +
            " In: " + inQuantity.toPlainString() +
            " Out: " + outQuantity.toPlainString());*/

            return startQuantity.add(inQuantity).subtract(outQuantity);
        }
    }

    public BigDecimal getPointInTimeCost(final UUID stockItemId, final Date queryDate) {
/*        System.out.println("Entering StockItemWorker.getPointInTimeCost() with " +
                stockItemId.toString() +
                " and " +
                queryDate.toString());*/
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            TypedQuery<PriceUpdateItem> startQuery = em.createQuery(
                    "select s from works$PriceUpdateItem s " +
                            "where s.stockItem.id = ?1 " +
                            "and s.priceUpdate.documentOn < ?2 " +
                            "and s.priceUpdate.currentStatus = ?3 " +
                            "order by s.priceUpdate.documentOn desc", PriceUpdateItem.class);

            startQuery.setParameter(1, stockItemId);
            startQuery.setParameter(2, queryDate);
            startQuery.setParameter(3, DocumentStatus.Accepted);
            startQuery.setMaxResults(1);

            PriceUpdateItem startTransaction = startQuery.getFirstResult();

            BigDecimal startCost = startTransaction != null ? startTransaction.getPrice() : BigDecimal.ZERO;

            return startCost;
        }
    }

    public BigDecimal getPeriodUsage(final UUID stockItemId, final Date dateInPeriod) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(dateInPeriod);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date firstOfMonth = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);

        Date lastOfMonth = cal.getTime();

/*
    * We cannot use this as Entities return old Date format
       LocalDate firstOfMonth = dateInPeriod.withDayOfMonth(1);
       LocalDate firstOfNextMonth = firstOfMonth.plusMonths(1);
       LocalDate lastOfMonth = firstOfNextMonth.minusDays(1);
*/

        BigDecimal usedQuantity = getOutQuantityRMWO(stockItemId, firstOfMonth, lastOfMonth)
                .add(getOutQuanityRMSO(stockItemId, firstOfMonth, lastOfMonth))
                .add(getOutQuantityCWO(stockItemId, firstOfMonth, lastOfMonth))
                .add(getOutQuantityCDO(stockItemId, firstOfMonth, lastOfMonth))
                .add(getOutQuantityCSO(stockItemId, firstOfMonth, lastOfMonth));

        return usedQuantity;

    }
    
    public void calculateUsage() {
        String s = "2017-07-01";
        String e = "2017-10-31";
        LocalDate start = LocalDate.parse(s);
        LocalDate end = LocalDate.parse(e);

        List<LocalDate> dates = new ArrayList<>();

        while (!start.isAfter(end)) {
            dates.add(start);
            updateRawMaterialUsage(toolsService.asDate(start));
            start = start.plusDays(1);
        }
    }

    public void updateRawMaterialUsage(final Date myDate) {

        List<RawMaterial> rawMaterials;

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            TypedQuery<RawMaterial> rawMaterialTypedQuery = em.createQuery(
                    "select e from works$RawMaterial e " +
                    "where e.delete_ts is null", RawMaterial.class
            );

            rawMaterials = rawMaterialTypedQuery.getResultList();

            for (RawMaterial rm : rawMaterials) {
                BigDecimal du = getDayUsage(rm.getUuid(), myDate);

                log.info(rm.getCode() + " = " + du.toPlainString());
            }
        }
    }

    public BigDecimal getDayUsage(final UUID stockItemId, final Date myDate) {

        BigDecimal usedQuantity = BigDecimal.ZERO
                .add(getOutQuantityRMWO(stockItemId, myDate, myDate))
                .add(getOutQuanityRMSO(stockItemId, myDate, myDate))
                .add(getOutQuantityCWO(stockItemId, myDate, myDate))
                .add(getOutQuantityCDO(stockItemId, myDate, myDate))
                .add(getOutQuantityCSO(stockItemId, myDate, myDate));

        return usedQuantity != null ? usedQuantity : BigDecimal.ZERO;
    }

    private BigDecimal getOutQuantityRMWO(final UUID stockItemId, final Date fromDate, final Date toDate) {

        Date beginOfFromDate = toolsService.beginOfDay(fromDate);
        Date endOfToDate = toolsService.endOfDay(toDate);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query rawMaterialQuery = em.createQuery(
                    "select sum(i.mass) from works$WorksOrderIngredient i " +
                            "where i.rawMaterial.id = ?1 " +
                            "and i.worksOrder.documentOn >= ?2 " +
                            "and i.worksOrder.documentOn <= ?3 " +
                            "and i.worksOrder.currentStatus = ?4 "
            );
            rawMaterialQuery.setParameter(1, stockItemId);
            rawMaterialQuery.setParameter(2, beginOfFromDate);
            rawMaterialQuery.setParameter(3, endOfToDate);
            rawMaterialQuery.setParameter(4, DocumentStatus.Accepted);
            BigDecimal outQuantity = (BigDecimal) rawMaterialQuery.getSingleResult();

            return outQuantity != null ? outQuantity : BigDecimal.ZERO;
        }

    }

    private BigDecimal getOutQuanityRMSO(final UUID stockItemId, final Date fromDate, final Date toDate) {

        Date beginOfFromDate = toolsService.beginOfDay(fromDate);
        Date endOfToDate = toolsService.endOfDay(toDate);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query rawMaterialQuery = em.createQuery(
                    "select sum(i.quantity) from works$SalesOrderRawMaterial i " +
                            "where i.rawMaterial.id = ?1 " +
                            "and i.salesOrder.documentOn >= ?2 " +
                            "and i.salesOrder.documentOn <= ?3 " +
                            "and i.salesOrder.currentStatus = ?4 "
            );
            rawMaterialQuery.setParameter(1, stockItemId);
            rawMaterialQuery.setParameter(2, beginOfFromDate);
            rawMaterialQuery.setParameter(3, endOfToDate);
            rawMaterialQuery.setParameter(4, DocumentStatus.Accepted);
            BigDecimal outQuantity = (BigDecimal) rawMaterialQuery.getSingleResult();

            return outQuantity != null ? outQuantity : BigDecimal.ZERO;
        }
    }

    private BigDecimal getOutQuantityCWO(final UUID stockItemId, final Date fromDate, final Date toDate) {

        Date beginOfFromDate = toolsService.beginOfDay(fromDate);
        Date endOfToDate = toolsService.endOfDay(toDate);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query rawMaterialQuery = em.createQuery(
                    "select sum(i.quantity) from works$WorksOrderPacking i " +
                            "where i.container.id = ?1 " +
                            "and i.worksOrder.documentOn >= ?2 " +
                            "and i.worksOrder.documentOn <= ?3 " +
                            "and i.worksOrder.currentStatus = ?4 "
            );
            rawMaterialQuery.setParameter(1, stockItemId);
            rawMaterialQuery.setParameter(2, beginOfFromDate);
            rawMaterialQuery.setParameter(3, endOfToDate);
            rawMaterialQuery.setParameter(4, DocumentStatus.Accepted);

            Long outQuantity = (Long) rawMaterialQuery.getSingleResult();

            return outQuantity != null ? BigDecimal.valueOf(outQuantity) : BigDecimal.ZERO;
        }

    }

    private BigDecimal getOutQuantityCSO(final UUID stockItemId, final Date fromDate, final Date toDate) {

        Date beginOfFromDate = toolsService.beginOfDay(fromDate);
        Date endOfToDate = toolsService.endOfDay(toDate);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query rawMaterialQuery = em.createQuery(
                    "select sum(i.quantity) from works$SalesOrderContainer i " +
                            "where i.container.id = ?1 " +
                            "and i.salesOrder.documentOn >= ?2 " +
                            "and i.salesOrder.documentOn <= ?3 " +
                            "and i.salesOrder.currentStatus = ?4 "
            );
            rawMaterialQuery.setParameter(1, stockItemId);
            rawMaterialQuery.setParameter(2, beginOfFromDate);
            rawMaterialQuery.setParameter(3, endOfToDate);
            rawMaterialQuery.setParameter(4, DocumentStatus.Accepted);
            BigDecimal outQuantity = (BigDecimal) rawMaterialQuery.getSingleResult();

            return outQuantity != null ? outQuantity : BigDecimal.ZERO;
        }
    }

    private BigDecimal getOutQuantityCDO(final UUID stockItemId, final Date fromDate, final Date toDate) {
        Date beginOfFromDate = toolsService.beginOfDay(fromDate);
        Date endOfToDate = toolsService.endOfDay(toDate);
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query rawMaterialQuery = em.createQuery(
                    "select sum(i.quantity) from works$DecantingOrderTarget i " +
                            "where i.container.id = ?1 " +
                            "and i.decantingOrder.documentOn >= ?2 " +
                            "and i.decantingOrder.documentOn <= ?3 " +
                            "and i.decantingOrder.currentStatus = ?4 "
            );
            rawMaterialQuery.setParameter(1, stockItemId);
            rawMaterialQuery.setParameter(2, beginOfFromDate);
            rawMaterialQuery.setParameter(3, endOfToDate);
            rawMaterialQuery.setParameter(4, DocumentStatus.Accepted);

            Long outQuantity = (Long) rawMaterialQuery.getSingleResult();

            return outQuantity != null ? BigDecimal.valueOf(outQuantity) : BigDecimal.ZERO;
        }
    }

    private BigDecimal getInQuantity(final UUID stockItemId, final Date fromDate, final Date toDate) {

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query stockIntakeQuery = em.createQuery(
                    "select sum(s.quantity) from works$StockIntakeItem s " +
                            "where s.stockItem.id = ?1 " +
                            "and s.stockIntake.documentOn >= ?2 " +
                            "and s.stockIntake.documentOn <= ?3 " +
                            "and s.stockIntake.currentStatus = ?4 "
            );
            stockIntakeQuery.setParameter(1, stockItemId);
            stockIntakeQuery.setParameter(2, fromDate);
            stockIntakeQuery.setParameter(3, toDate);
            stockIntakeQuery.setParameter(4, DocumentStatus.Accepted);

            BigDecimal inQuantity = (BigDecimal) stockIntakeQuery.getSingleResult();

            return inQuantity != null ? inQuantity : BigDecimal.ZERO;
        }
    }
}
