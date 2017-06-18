/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.*;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.math.BigDecimal;
import com.haulmont.chile.core.annotations.MetaProperty;

@NamePattern("%s %s|code,description")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "WORKS_STOCK_ITEM")
@Entity(name = "works$StockItem")
public class StockItem extends StandardEntity {
    private static final long serialVersionUID = 5894257941697065114L;

    @Column(name = "CODE", nullable = false, unique = true, length = 30)
    protected String code;

    @Transient
    @MetaProperty
    protected Integer codeNumber;

    @Column(name = "DESCRIPTION", length = 100)
    protected String description;

    @Column(name = "REORDER", nullable = false)
    protected BigDecimal reorder = BigDecimal.ZERO;

    @Column(name = "MAX_STOCK", nullable = false)
    protected BigDecimal maxStock = BigDecimal.ZERO;

    @Column(name = "UNIT", nullable = false)
    protected String unit;

    @Column(name = "CURRENT_STATUS", nullable = false)
    protected String currentStatus;

    public Unit getUnit() {
        return unit == null ? null : Unit.fromId(unit);
    }

    public void setUnit(Unit unit) {
        this.unit = unit == null ? null : unit.getId();
    }

    public StockItemStatus getCurrentStatus() {
        return currentStatus == null ? null : StockItemStatus.fromId(currentStatus);
    }

    public void setCurrentStatus(StockItemStatus currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.getId();
    }



    public Integer getCodeNumber() {

        String myString = getCode().replaceAll("\\D+", "");
        if (myString.isEmpty()) {
            myString = "0";
        }
        Integer codeNumber = Integer.valueOf(myString);

        
        return codeNumber;
    }

    public BigDecimal getReorder() {
        return reorder;
    }

    public void setReorder(BigDecimal reorder) {
        this.reorder = reorder;
    }


    public BigDecimal getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(BigDecimal maxStock) {
        this.maxStock = maxStock;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}