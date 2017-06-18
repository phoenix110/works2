/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@NamePattern("%s %s|product,rawMaterial")
@Table(name = "WORKS_FORMULA", uniqueConstraints = {
    @UniqueConstraint(name = "IDX_WORKS_FORMULA_UNQ", columnNames = {"PRODUCT_ID", "SEQUENCE_NO"})
})
@Entity(name = "works$Formula")
public class Formula extends StandardEntity {
    private static final long serialVersionUID = -4737369452984699971L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "SEQUENCE_NO", nullable = false)
    protected Integer sequenceNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RAW_MATERIAL_ID")
    protected RawMaterial rawMaterial;

    @MetaProperty(datatype = PartsPer100Datatype.NAME, mandatory = true)
    @Column(name = "PARTS_PER100", nullable = false)
    protected BigDecimal partsPer100 = BigDecimal.ZERO;

    public BigDecimal getPartsPer100() {
        return partsPer100;
    }

    public void setPartsPer100(BigDecimal partsPer100) {
        this.partsPer100 = partsPer100;
    }
    
    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }


}