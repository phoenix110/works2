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

@NamePattern("%s|rawMaterial")
@Table(name = "WORKS_WORKS_ORDER_INGREDIENT")
@Entity(name = "works$WorksOrderIngredient")
public class WorksOrderIngredient extends StandardEntity {
    private static final long serialVersionUID = 2078304513640186093L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WORKS_ORDER_ID")
    protected WorksOrder worksOrder;

    @Column(name = "SEQUENCE_NO", nullable = false)
    protected Integer sequenceNo = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RAW_MATERIAL_ID")
    protected RawMaterial rawMaterial;

    @MetaProperty(datatype = PartsPer100Datatype.NAME, mandatory = true)
    @Column(name = "MASS", nullable = false)
    protected BigDecimal mass = BigDecimal.ZERO;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "KG_COST", nullable = false)
    protected BigDecimal kgCost = BigDecimal.ZERO;

    @Transient
    @MetaProperty(datatype = CurrencyDatatype.NAME, related = {"mass", "kgCost"})
    protected BigDecimal lineCost;

    public void setWorksOrder(WorksOrder worksOrder) {
        this.worksOrder = worksOrder;
    }

    public WorksOrder getWorksOrder() {
        return worksOrder;
    }

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

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setKgCost(BigDecimal kgCost) {
        this.kgCost = kgCost;
    }

    public BigDecimal getKgCost() {
        return kgCost;
    }

    public BigDecimal getLineCost() {

        return getMass().multiply(getKgCost());
    }


}