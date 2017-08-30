package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s %s|documentNo,description")
@Entity(name = "works$DecantingOrder")
public class DecantingOrder extends Order {
    private static final long serialVersionUID = 4224302202755481439L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DECANTED_PRODUCT_ID")
    protected Product decantedProduct;

    @Column(name = "INFORMATION")
    protected String information;

    @Column(name = "SOURCE_VOLUME")
    protected BigDecimal sourceVolume = BigDecimal.ZERO;

    @Column(name = "TARGET_VOLUME")
    protected BigDecimal targetVolume = BigDecimal.ZERO;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "decantingOrder")
    protected List<DecantingOrderSource> decantingOrderSources;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "decantingOrder")
    protected List<DecantingOrderTarget> decantingOrderTargets;


    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }


    public void setDecantedProduct(Product decantedProduct) {
        this.decantedProduct = decantedProduct;
    }

    public Product getDecantedProduct() {
        return decantedProduct;
    }


    public void setSourceVolume(BigDecimal sourceVolume) {
        this.sourceVolume = sourceVolume;
    }

    public BigDecimal getSourceVolume() {
        return sourceVolume;
    }

    public void setTargetVolume(BigDecimal targetVolume) {
        this.targetVolume = targetVolume;
    }

    public BigDecimal getTargetVolume() {
        return targetVolume;
    }

    public void setDecantingOrderSources(List<DecantingOrderSource> decantingOrderSources) {
        this.decantingOrderSources = decantingOrderSources;
    }

    public List<DecantingOrderSource> getDecantingOrderSources() {
        return decantingOrderSources;
    }

    public void setDecantingOrderTargets(List<DecantingOrderTarget> decantingOrderTargets) {
        this.decantingOrderTargets = decantingOrderTargets;
    }

    public List<DecantingOrderTarget> getDecantingOrderTargets() {
        return decantingOrderTargets;
    }



}