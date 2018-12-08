package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s %s|product,container,priceOn")
@Table(name = "WORKS_PRICE_LIST")
@Entity(name = "works$PriceList")
public class PriceList extends StandardEntity {
    private static final long serialVersionUID = -1525972076098770460L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTAINER_ID")
    protected Container container;

    @Temporal(TemporalType.DATE)
    @Column(name = "PRICE_ON", nullable = false)
    protected Date priceOn;

    @NotNull
    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "RAW_MATERIAL_COST", nullable = false)
    protected BigDecimal rawMaterialCost;

    @NotNull
    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "CONTAINER_COST", nullable = false)
    protected BigDecimal containerCost;

    @MetaProperty(datatype = "currency")
    @Column(name = "PACKING_COST")
    protected BigDecimal packingCost;

    @NotNull
    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "OVERHEAD_COST", nullable = false)
    protected BigDecimal overheadCost;

    @NotNull
    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "LABEL_COST", nullable = false)
    protected BigDecimal labelCost;

    @MetaProperty(datatype = CurrencyDatatype.NAME, mandatory = true)
    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    public void setPackingCost(BigDecimal packingCost) {
        this.packingCost = packingCost;
    }

    public BigDecimal getPackingCost() {
        return packingCost;
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

    public void setOverheadCost(BigDecimal overheadCost) {
        this.overheadCost = overheadCost;
    }

    public BigDecimal getOverheadCost() {
        return overheadCost;
    }

    public void setLabelCost(BigDecimal labelCost) {
        this.labelCost = labelCost;
    }

    public BigDecimal getLabelCost() {
        return labelCost;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setPriceOn(Date priceOn) {
        this.priceOn = priceOn;
    }

    public Date getPriceOn() {
        return priceOn;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }


}