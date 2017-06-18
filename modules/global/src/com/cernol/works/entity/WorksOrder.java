package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.Composition;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity(name = "works$WorksOrder")
public class WorksOrder extends Order {
    private static final long serialVersionUID = 705235975323362925L;

    @Column(name = "PRODUCT")
    protected String product;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OnDelete(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MIXER_ID")
    protected Mixer mixer;

    @Column(name = "BATCH_QUANTITY", nullable = false)
    protected Integer batchQuantity;

    @Column(name = "MANUFACTURING_KEY")
    protected String manufacturingKey;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "worksOrder")
    protected List<WorksOrderPacking> worksOrderPackings;

    @OrderBy("sequenceNo")
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "worksOrder")
    protected List<WorksOrderIngredient> worksOrderIngredients;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "worksOrder")
    protected List<WorksOrderLable> orderLables;

    public void setWorksOrderPackings(List<WorksOrderPacking> worksOrderPackings) {
        this.worksOrderPackings = worksOrderPackings;
    }

    public List<WorksOrderPacking> getWorksOrderPackings() {
        return worksOrderPackings;
    }

    public void setWorksOrderIngredients(List<WorksOrderIngredient> worksOrderIngredients) {
        this.worksOrderIngredients = worksOrderIngredients;
    }

    public List<WorksOrderIngredient> getWorksOrderIngredients() {
        return worksOrderIngredients;
    }

    public void setOrderLables(List<WorksOrderLable> orderLables) {
        this.orderLables = orderLables;
    }

    public List<WorksOrderLable> getOrderLables() {
        return orderLables;
    }


    public void setManufacturingKey(ManufacturingKey manufacturingKey) {
        this.manufacturingKey = manufacturingKey == null ? null : manufacturingKey.getId();
    }

    public ManufacturingKey getManufacturingKey() {
        return manufacturingKey == null ? null : ManufacturingKey.fromId(manufacturingKey);
    }


    public void setMixer(Mixer mixer) {
        this.mixer = mixer;
    }

    public Mixer getMixer() {
        return mixer;
    }

    public void setBatchQuantity(Integer batchQuantity) {
        this.batchQuantity = batchQuantity;
    }

    public Integer getBatchQuantity() {
        return batchQuantity;
    }


    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }


}