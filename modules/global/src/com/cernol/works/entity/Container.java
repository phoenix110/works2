/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import java.math.BigDecimal;
import javax.persistence.Column;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import com.haulmont.chile.core.annotations.MetaProperty;

@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_CONTAINER")
@NamePattern("%s %s|code,description")
@Entity(name = "works$Container")
public class Container extends StockItem {
    private static final long serialVersionUID = 7286607361352282003L;

    @Column(name = "CAPACITY", nullable = false)
    protected BigDecimal capacity = BigDecimal.ZERO;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "COST_PER_UNIT", nullable = false)
    protected BigDecimal costPerUnit = BigDecimal.ZERO;

    public void setCostPerUnit(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public BigDecimal getCostPerUnit() {
        return costPerUnit;
    }


    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }


}