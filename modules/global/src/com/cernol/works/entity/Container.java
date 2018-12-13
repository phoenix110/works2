/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import javax.persistence.Column;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_CONTAINER")
@NamePattern("%s %s|code,description")
@Entity(name = "works$Container")
public class Container extends StockItem {
    private static final long serialVersionUID = 7286607361352282003L;

    @Column(name = "CAPACITY", nullable = false)
    protected BigDecimal capacity = BigDecimal.ZERO;


    @OneToMany(mappedBy = "container")
    protected List<ProductContainer> productContainers;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACKING_ID")
    protected Packing packing;

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