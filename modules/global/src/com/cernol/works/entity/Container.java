/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_CONTAINER")
@NamePattern("%s %s|code,description")
@Entity(name = "works$Container")
public class Container extends StockItem {
    private static final long serialVersionUID = 7286607361352282003L;

    @Column(name = "CAPACITY", nullable = false)
    protected BigDecimal capacity = BigDecimal.ZERO;


    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACKING_ID")
    protected Packing packing;

    @Column(name = "UNITS_PER_SHIPPER")
    protected Integer unitsPerShipper;

    @OneToMany(mappedBy = "container")
    protected List<ProductContainer> productContainers;

    public void setUnitsPerShipper(Integer unitsPerShipper) {
        this.unitsPerShipper = unitsPerShipper;
    }

    public Integer getUnitsPerShipper() {
        return unitsPerShipper;
    }


    public void setPacking(Packing packing) {
        this.packing = packing;
    }

    public Packing getPacking() {
        return packing;
    }


    public void setProductContainers(List<ProductContainer> productContainers) {
        this.productContainers = productContainers;
    }

    public List<ProductContainer> getProductContainers() {
        return productContainers;
    }





    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }


}