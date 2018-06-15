package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern("%s|manufacturingKey")
@Table(name = "WORKS_WORKS_ORDER_KEY")
@Entity(name = "works$WorksOrderKey")
public class WorksOrderKey extends StandardEntity {
    private static final long serialVersionUID = 4146210638278544208L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WORKS_ORDER_ID")
    protected WorksOrder worksOrder;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANUFACTURING_KEY_ID")
    protected Instruction manufacturingKey;

    public Instruction getManufacturingKey() {
        return manufacturingKey;
    }

    public void setManufacturingKey(Instruction manufacturingKey) {
        this.manufacturingKey = manufacturingKey;
    }



    public void setWorksOrder(WorksOrder worksOrder) {
        this.worksOrder = worksOrder;
    }

    public WorksOrder getWorksOrder() {
        return worksOrder;
    }


}