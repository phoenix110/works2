package com.cernol.works.service;

import com.cernol.works.entity.DocumentStatus;
import com.cernol.works.entity.WorksOrder;
import com.haulmont.cuba.core.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class OrderWorker {
    public static final String NAME = "works_StockItemWorker";

    @Inject
    private Persistence persistence;

    public BigDecimal getProductionQuantity(UUID productId, LocalDate fromDate, LocalDate toDate) {

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            Query startQuery = em.createQuery(
                    "select sum(o.mass) from works$WorksOrder o " +
                            "where o.product.id = ?1 " +
                            "and o.documentOn between ?2 and ?3" +
                            "and o.documentStatus = ?4 ");

            startQuery.setParameter(1, productId);
            startQuery.setParameter(2, fromDate);
            startQuery.setParameter(3, toDate);
            startQuery.setParameter(4, DocumentStatus.Accepted);

            BigDecimal quantity = (BigDecimal) startQuery.getSingleResult();

            return quantity;
        }



    }
}
