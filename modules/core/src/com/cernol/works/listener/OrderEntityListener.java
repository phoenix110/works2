package com.cernol.works.listener;

import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.cernol.works.entity.Order;

import javax.inject.Inject;

@Component("works_OrderEntityListener")
public class OrderEntityListener implements BeforeInsertEntityListener<Order> {

    @Inject
    private UniqueNumbersAPI uniqueNumbersAPI;


    @Override
    public void onBeforeInsert(Order entity, EntityManager entityManager) {
        entity.setDocumentNo(String.format("%06d",uniqueNumbersAPI.getNextNumber("OrderNumber")));
    }


}