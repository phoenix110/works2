package com.cernol.works.listener;

import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.cernol.works.entity.WorksOrder;

import javax.inject.Inject;

@Component("works_WorksOrderEntityListener")
public class WorksOrderEntityListener implements BeforeInsertEntityListener<WorksOrder> {

    @Inject
    private UniqueNumbersAPI uniqueNumbersAPI;

    @Override
    public void onBeforeInsert(WorksOrder entity, EntityManager entityManager) {
        entity.setDocumentNo(String.format("%06d",uniqueNumbersAPI.getNextNumber("OrderNumber")));
    }


}