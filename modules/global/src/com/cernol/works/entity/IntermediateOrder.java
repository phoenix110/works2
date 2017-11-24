package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.OneToOne;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s %s|documentNo,description")
@Entity(name = "works$IntermediateOrder")
public class IntermediateOrder extends Order {
    private static final long serialVersionUID = -5833198010588566064L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MIXER_ID")
    protected Mixer mixer;

    @Column(name = "BATCH_QUANTITY", nullable = false)
    protected Integer batchQuantity;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "intermediateOrder")
    protected List<IntermediateOrderIngredient> intermediateOrderIngredients;

    public void setIntermediateOrderIngredients(List<IntermediateOrderIngredient> intermediateOrderIngredients) {
        this.intermediateOrderIngredients = intermediateOrderIngredients;
    }

    public List<IntermediateOrderIngredient> getIntermediateOrderIngredients() {
        return intermediateOrderIngredients;
    }



    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
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


}