package com.cernol.works.service;

import com.cernol.works.entity.StockCountItem;
import com.cernol.works.entity.StockIntakeItem;
import com.haulmont.cuba.core.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by skopp on 28/06/2017.
 */
@Component(StockItemWorker.NAME)
public class StockItemWorker {
    public static final String NAME = "works_StockItemWorker";

    @Inject
    private Persistence persistence;

    public BigDecimal getCurrentQuantity(final UUID stockItemId, final Date queryDate) {
        System.out.println("Entering StockItemWorker.getCurrentQuantity() with " +
                stockItemId.toString() +
                " and " +
                queryDate.toString());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);

        Date beginDate = cal.getTime();

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            TypedQuery<StockCountItem> startQuery = em.createQuery(
                    "select s from works$StockCountItem s " +
                            "where s.stockItem.id = ?1 " +
                            "and s.stockCount.documentOn < ?2 " +
                            "order by s.stockCount.documentOn desc", StockCountItem.class);

            startQuery.setParameter(1, stockItemId);
            startQuery.setParameter(2, queryDate);
            startQuery.setMaxResults(1);

            StockCountItem startTransaction = startQuery.getFirstResult();

            BigDecimal startQuantity = startTransaction != null ? startTransaction.getCountedQuantity() : BigDecimal.ZERO;

            Date sinceDate = startTransaction != null ? startTransaction.getStockCount().getDocumentOn() : beginDate;

            BigDecimal outQuantity = getOutQuantity(stockItemId, sinceDate, queryDate);

            BigDecimal inQuantity = getInQuantity(stockItemId, sinceDate, queryDate);

            return startQuantity.add(inQuantity).subtract(outQuantity);
        }
    }

    public BigDecimal getOutQuantity(final UUID stockItemId, final Date fromDate, final Date toDate) {

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query rawMaterialQuery = em.createQuery(
                    "select sum(i.mass) from works$WorksOrderIngredient i " +
                            "where i.rawMaterial.id = ?1 " +
                            "and i.worksOrder.documentOn >= ?2 " +
                            "and i.worksOrder.documentOn < ?3 "
            );
            rawMaterialQuery.setParameter(1, stockItemId);
            rawMaterialQuery.setParameter(2, fromDate);
            rawMaterialQuery.setParameter(3, toDate);
            BigDecimal outQuantity = (BigDecimal) rawMaterialQuery.getSingleResult();

            return outQuantity !=null ? outQuantity : BigDecimal.ZERO;
        }

    }

    public BigDecimal getInQuantity(final UUID stockItemId, final Date fromDate, final Date toDate) {

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query stockIntakeQuery = em.createQuery(
                    "select sum(s.quantity) from works$StockIntakeItem s " +
                            "where s.stockItem.id = ?1 " +
                            "and s.stockIntake.documentOn >= ?2 " +
                            "and s.stockIntake.documentOn < ?3 "
            );
            stockIntakeQuery.setParameter(1, stockItemId);
            stockIntakeQuery.setParameter(2, fromDate);
            stockIntakeQuery.setParameter(3, toDate);

            BigDecimal inQuantity = (BigDecimal) stockIntakeQuery.getSingleResult();

            return inQuantity !=null ? inQuantity : BigDecimal.ZERO;
        }
    }


    public BigDecimal getCurrentCost(final UUID stockItemId, final Date queryDate) {
        System.out.println("Entering StockItemWorker.getCurrentCost() with " +
                stockItemId.toString() +
                " and " +
                queryDate.toString());
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            TypedQuery<StockIntakeItem> startQuery = em.createQuery(
                    "select s from works$StockIntakeItem s " +
                            "where s.stockItem.id = ?1 " +
                            "and s.stockIntake.documentOn < ?2 " +
                            "order by s.stockIntake.documentOn desc", StockIntakeItem.class);

            startQuery.setParameter(1, stockItemId);
            startQuery.setParameter(2, queryDate);
            startQuery.setMaxResults(1);

            StockIntakeItem startTransaction = startQuery.getFirstResult();

            BigDecimal startCost = startTransaction != null ? startTransaction.getUnitPrice() : BigDecimal.ZERO;

            return startCost;
        }
    }
}
