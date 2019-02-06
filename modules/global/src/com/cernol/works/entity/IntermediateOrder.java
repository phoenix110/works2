package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s %s|documentNo,description")
@Entity(name = "works$IntermediateOrder")
public class IntermediateOrder extends Order {
    private static final long serialVersionUID = -5833198010588566064L;

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




}