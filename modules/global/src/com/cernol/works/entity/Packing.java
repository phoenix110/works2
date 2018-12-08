package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Column;

@NamePattern("%s|code")
@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_PACKING")
@Entity(name = "works$Packing")
public class Packing extends StockItem {
    private static final long serialVersionUID = 4615143441439099256L;

    @Column(name = "PACKING_UNITS")
    protected Integer packing_units;

    public void setPacking_units(Integer packing_units) {
        this.packing_units = packing_units;
    }

    public Integer getPacking_units() {
        return packing_units;
    }



}