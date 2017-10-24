package com.cernol.works.core.jmx;

import com.cernol.works.entity.RawMaterial;
import com.cernol.works.entity.StockItem;
import com.cernol.works.entity.StockUsage;
import com.cernol.works.service.StockItemWorker;
import com.cernol.works.service.ToolsService;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.security.app.Authenticated;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component("works_UsagesMBean")
public class Usages implements UsagesMBean {

    private Logger log = LoggerFactory.getLogger(Usages.class);

    @Inject
    private Persistence persistence;

    @Inject
    private Metadata metadata;

    @Inject
    protected TimeSource timeSource;

    @Inject
    private StockItemWorker stockItemWorker;

    @Inject
    private ToolsService toolsService;

    @Override
    @Authenticated
    public String calculateUsagesOn(String onDate) {
        try {
            List<RawMaterial> rawMaterials;
            LocalDate myLocalDate = LocalDate.parse(onDate);
            Date myDate = toolsService.asDate(myLocalDate);

            try (Transaction tx = persistence.createTransaction()) {
                EntityManager em = persistence.getEntityManager();
                TypedQuery<RawMaterial> rawMaterialTypedQuery = em.createQuery(
                        "select e from works$RawMaterial e " +
                                "where e.deleteTs is null " +
                                "order by e.code", RawMaterial.class
                );

                rawMaterials = rawMaterialTypedQuery.getResultList();

                for (RawMaterial rm : rawMaterials) {

                    BigDecimal du = stockItemWorker.getDayUsage(rm.getUuid(), myDate);

                    log.info(rm.getCode() + " = " + du.toString());

                    createOrUpdateStockUsage(rm, myDate, du);
                }
                tx.commit();
            }
            return "Updated usages for " + onDate;
        } catch (Throwable e) {
            log.error("Error calculating usages", e);
            return ExceptionUtils.getFullStackTrace(e);
        }

    }

    @Override
    @Authenticated
    public String calculateUsagesSince(String sinceDate) {
        try {
            List<RawMaterial> rawMaterials;
            LocalDate sinceLocalDate = LocalDate.parse(sinceDate);
            LocalDate untilLocalDate = toolsService.asLocalDate(timeSource.currentTimestamp());


            try (Transaction tx = persistence.createTransaction()) {
                EntityManager em = persistence.getEntityManager();
                TypedQuery<RawMaterial> rawMaterialTypedQuery = em.createQuery(
                        "select e from works$RawMaterial e " +
                                "where e.deleteTs is null " +
                                "order by e.code", RawMaterial.class
                );

                rawMaterials = rawMaterialTypedQuery.getResultList();

                for (RawMaterial rm : rawMaterials) {
                    LocalDate calcLocalDate = sinceLocalDate;

                    while (!calcLocalDate.isAfter(untilLocalDate)) {

                        Date myDate = toolsService.asDate(calcLocalDate);
                        BigDecimal du = stockItemWorker.getDayUsage(rm.getUuid(), myDate);

                        log.info(rm.getCode() +" : " + myDate.toString() +" = " + du.toString());

                        createOrUpdateStockUsage(rm, myDate, du);
                        calcLocalDate = calcLocalDate.plusDays(1);
                    }
                }
                tx.commit();
            }
            return "Updated usages since " + sinceDate;
        } catch (Throwable e) {
            log.error("Error calculating usages", e);
            return ExceptionUtils.getFullStackTrace(e);
        }

    }

    public void createOrUpdateStockUsage(StockItem stockItem, Date usageDate, BigDecimal quantity) {
        try (Transaction tx = persistence.createTransaction()){
            TypedQuery<StockUsage> query = persistence.getEntityManager().createQuery(
                    "select e from works$StockUsage e " +
                            "where e.stockItem.id = ?1 " +
                            "and e.usedOn = ?2 ",
                    StockUsage.class
            );

            query.setParameter(1, stockItem);
            query.setParameter(2, usageDate);

            List<StockUsage> stockUsages = query.getResultList();

            if (stockUsages.size() == 0) {
                StockUsage stockUsage = metadata.create(StockUsage.class);

                stockUsage.setUsedOn(usageDate);
                stockUsage.setStockItem(stockItem);
                stockUsage.setQuantity(quantity);

                persistence.getEntityManager().persist(stockUsage);
            } else  if (stockUsages.size() == 1) {
                StockUsage stockUsage = stockUsages.get(0);
                stockUsage.setQuantity(quantity);

            } else {
                throw new IllegalStateException("More than one Stock Usage entry for " + stockItem.getCode());
            }

            tx.commit();
        }
    }
}
