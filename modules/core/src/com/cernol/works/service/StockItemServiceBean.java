package com.cernol.works.service;

import com.cernol.works.entity.StockItem;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Service(StockItemService.NAME)
public class StockItemServiceBean implements StockItemService {

    private Logger log = LoggerFactory.getLogger(StockItemServiceBean.class);

    private final Persistence persistence;

    @Inject
    public StockItemServiceBean(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    public BigDecimal getCurrentCost(UUID stockItemId, Date date) {

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();

            TypedQuery<StockItem> startQuery = em.createQuery(
                    "select s from works$StockItem", StockItem.class);

            startQuery.setMaxResults(1);



            return BigDecimal.ZERO;


        }

    }


}