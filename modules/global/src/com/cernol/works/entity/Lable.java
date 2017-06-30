/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.*;
import java.math.BigDecimal;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;

@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_LABLE")
@Entity(name = "works$Lable")
public class Lable extends StockItem {
    private static final long serialVersionUID = -2301445352972513316L;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "UNIT_COST", nullable = false)
    protected BigDecimal unitCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_FILE_ID")
    protected FileDescriptor imageFile;

    public void setImageFile(FileDescriptor imageFile) {
        this.imageFile = imageFile;
    }

    public FileDescriptor getImageFile() {
        return imageFile;
    }
    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }


}