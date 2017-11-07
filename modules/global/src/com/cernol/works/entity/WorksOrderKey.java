package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|worksOrder,manufacturingKey")
@Table(name = "WORKS_WORKS_ORDER_KEY")
@Entity(name = "works$WorksOrderKey")
public class WorksOrderKey extends StandardEntity {
    private static final long serialVersionUID = 4146210638278544208L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WORKS_ORDER_ID")
    protected WorksOrder worksOrder;

    @Column(name = "MANUFACTURING_KEY", nullable = false)
    protected String manufacturingKey;

    public void setWorksOrder(WorksOrder worksOrder) {
        this.worksOrder = worksOrder;
    }

    public WorksOrder getWorksOrder() {
        return worksOrder;
    }

    public void setManufacturingKey(ManufacturingKey manufacturingKey) {
        this.manufacturingKey = manufacturingKey == null ? null : manufacturingKey.getId();
    }

    public ManufacturingKey getManufacturingKey() {
        return manufacturingKey == null ? null : ManufacturingKey.fromId(manufacturingKey);
    }


}