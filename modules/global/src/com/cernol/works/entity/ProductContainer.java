package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;

@NamePattern("%s %s|product,container")
@Table(name = "WORKS_PRODUCT_CONTAINER")
@Entity(name = "works$ProductContainer")
public class ProductContainer extends StandardEntity {
    private static final long serialVersionUID = -5418068874391332316L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTAINER_ID")
    protected Container container;

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


}