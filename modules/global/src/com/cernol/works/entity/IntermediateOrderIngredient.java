package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|rawMaterial")
@Table(name = "WORKS_INTERMEDIATE_ORDER_INGREDIENT")
@Entity(name = "works$IntermediateOrderIngredient")
public class IntermediateOrderIngredient extends StandardEntity {
    private static final long serialVersionUID = -3590777880714469824L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INTERMEDIATE_ORDER_ID")
    protected IntermediateOrder intermediateOrder;

    @Column(name = "SEQUENCE_NO", nullable = false)
    protected Integer sequenceNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RAW_MATERIAL_ID")
    protected RawMaterial rawMaterial;

    @MetaProperty(datatype = "partsPer100", mandatory = true)
    @Column(name = "MASS", nullable = false)
    protected BigDecimal mass;

    @MetaProperty(datatype = "partsPer100", mandatory = true)
    @Column(name = "PARTS_PER100", nullable = false)
    protected BigDecimal partsPer100;

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }


    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }


    public void setIntermediateOrder(IntermediateOrder intermediateOrder) {
        this.intermediateOrder = intermediateOrder;
    }

    public IntermediateOrder getIntermediateOrder() {
        return intermediateOrder;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setPartsPer100(BigDecimal partsPer100) {
        this.partsPer100 = partsPer100;
    }

    public BigDecimal getPartsPer100() {
        return partsPer100;
    }


}