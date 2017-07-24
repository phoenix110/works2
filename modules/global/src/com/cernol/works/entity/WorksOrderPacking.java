package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|container")
@Table(name = "WORKS_WORKS_ORDER_PACKING")
@Entity(name = "works$WorksOrderPacking")
public class WorksOrderPacking extends StandardEntity {
    private static final long serialVersionUID = 3070466929016939392L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WORKS_ORDER_ID")
    protected WorksOrder worksOrder;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTAINER_ID")
    protected Container container;

    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "UNIT_COST", nullable = false)
    protected BigDecimal unitCost = BigDecimal.ZERO;

    @Column(name = "CUSTOMERS_OWN")
    protected Boolean customersOwn = Boolean.FALSE;

    @Column(name = "ADDITIONAL")
    protected Boolean additional = Boolean.FALSE;

    @Transient
    @MetaProperty(datatype = CurrencyDatatype.NAME,
    related = {"quantity", "container", "customersOwn"})
    protected BigDecimal lineCost = BigDecimal.ZERO;

    @Transient
    @MetaProperty(related = {"quantity", "container", "additional"})
    protected BigDecimal lineCapacity = BigDecimal.ZERO;

    public void setWorksOrder(WorksOrder worksOrder) {
        this.worksOrder = worksOrder;
    }

    public WorksOrder getWorksOrder() {
        return worksOrder;
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

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setCustomersOwn(Boolean customersOwn) {
        this.customersOwn = customersOwn;
    }

    public Boolean getCustomersOwn() {
        return customersOwn;
    }

    public void setAdditional(Boolean additional) {
        this.additional = additional;
    }

    public Boolean getAdditional() {
        return additional;
    }

    public BigDecimal getLineCost() {
        if (getContainer() == null) {
            return BigDecimal.ZERO;
        }

        if (getCustomersOwn()) {
            return BigDecimal.ZERO;
        } else {
            return getUnitCost().multiply(BigDecimal.valueOf(getQuantity()));
        }
    }

    public BigDecimal getLineCapacity() {
        if (getContainer() == null) {
            return BigDecimal.ZERO;
        }
        if (getAdditional()) {
            return BigDecimal.ZERO;
        }
        else {
            return getContainer().getCapacity().multiply(BigDecimal.valueOf(getQuantity()));
        }
    }


}