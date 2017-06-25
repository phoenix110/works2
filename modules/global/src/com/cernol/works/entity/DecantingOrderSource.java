package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.MetaProperty;

import java.math.BigDecimal;
import javax.persistence.Transient;

@NamePattern("%s|container")
@Table(name = "WORKS_DECANTING_ORDER_SOURCE")
@Entity(name = "works$DecantingOrderSource")
public class DecantingOrderSource extends StandardEntity {
    private static final long serialVersionUID = 636802335332495769L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DECANTING_ORDER_ID")
    protected DecantingOrder decantingOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTAINER_ID")
    protected Container container;

    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity = 0;

    @Transient
    @MetaProperty(related = {"container", "quantity"})
    protected BigDecimal lineCapacity;

    public BigDecimal getLineCapacity() {

        if (getContainer() == null) {
            return BigDecimal.ZERO;
        } else {
            return getContainer().getCapacity().multiply(BigDecimal.valueOf(getQuantity()));
        }
    }



    public void setDecantingOrder(DecantingOrder decantingOrder) {
        this.decantingOrder = decantingOrder;
    }

    public DecantingOrder getDecantingOrder() {
        return decantingOrder;
    }


    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }


}