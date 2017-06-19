package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.DiscriminatorColumn;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@NamePattern("%s %s|documentNo,description")
@Table(name = "WORKS_ORDER")
@Entity(name = "works$Order")
public class Order extends StandardEntity {
    private static final long serialVersionUID = 9160207427728061320L;

    @Column(name = "DOCUMENT_NO", nullable = false, unique = true, length = 10)
    protected String documentNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOCUMENT_ON", nullable = false)
    protected Date documentOn;

    @Column(name = "DESCRIPTION", nullable = false)
    protected String description;

    @Column(name = "UNIT", nullable = false)
    protected String unit = Unit.Litre.toString();

    @MetaProperty(datatype = PartsPer100Datatype.NAME, mandatory = true)
    @Column(name = "VOLUME", nullable = false)
    protected BigDecimal volume = BigDecimal.ZERO;

    @Column(name = "MASS", nullable = false)
    protected BigDecimal mass = BigDecimal.ZERO;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "RAW_MATERIAL_COST", nullable = false)
    protected BigDecimal rawMaterialCost = BigDecimal.ZERO;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "CONTAINER_COST", nullable = false)
    protected BigDecimal containerCost = BigDecimal.ZERO;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "LABLE_COST", nullable = false)
    protected BigDecimal lableCost = BigDecimal.ZERO;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "OVERHEAD_COST", nullable = false)
    protected BigDecimal overheadCost = BigDecimal.ZERO;

    @Transient
    @MetaProperty(datatype = CurrencyDatatype.NAME,
            mandatory = true,
            related = {"rawMaterialCost", "containerCost", "lableCost", "overheadCost"})
    protected BigDecimal totalCost = BigDecimal.ZERO;

    @Column(name = "CURRENT_STATUS", nullable = false)
    protected String currentStatus = DocumentStatus.New.toString();

    public DocumentStatus getCurrentStatus() {
        return currentStatus == null ? null : DocumentStatus.fromId(currentStatus);
    }

    public void setCurrentStatus(DocumentStatus currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.getId();
    }


    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentOn(Date documentOn) {
        this.documentOn = documentOn;
    }

    public Date getDocumentOn() {
        return documentOn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUnit(Unit unit) {
        this.unit = unit == null ? null : unit.getId();
    }

    public Unit getUnit() {
        return unit == null ? null : Unit.fromId(unit);
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setRawMaterialCost(BigDecimal rawMaterialCost) {
        this.rawMaterialCost = rawMaterialCost;
    }

    public BigDecimal getRawMaterialCost() {
        return rawMaterialCost;
    }

    public void setContainerCost(BigDecimal containerCost) {
        this.containerCost = containerCost;
    }

    public BigDecimal getContainerCost() {
        return containerCost;
    }

    public BigDecimal getLableCost() {
        return lableCost;
    }

    public void setLableCost(BigDecimal lableCost) {
        this.lableCost = lableCost;
    }

    public void setOverheadCost(BigDecimal overheadCost) {
        this.overheadCost = overheadCost;
    }

    public BigDecimal getOverheadCost() {
        return overheadCost;
    }

    public BigDecimal getTotalCost() {
        return getRawMaterialCost().add(getContainerCost()).add(getLableCost()).add(getOverheadCost());
    }


}