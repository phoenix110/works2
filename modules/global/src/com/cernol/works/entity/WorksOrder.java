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
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|documentNo,description")
@Entity(name = "works$WorksOrder")
public class WorksOrder extends Order {
    private static final long serialVersionUID = 705235975323362925L;




    @Column(name = "MANUFACTURING_KEY")
    protected String manufacturingKey;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "worksOrder")
    protected List<WorksOrderKey> worksOrderKeys;

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
    protected List<WorksOrderLable> worksOrderLables;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "worksOrder")
    protected List<WorksOrderShipper> worksOrderShippers;

    public List<WorksOrderShipper> getWorksOrderShippers() {
        return worksOrderShippers;
    }

    public void setWorksOrderShippers(List<WorksOrderShipper> worksOrderShippers) {
        this.worksOrderShippers = worksOrderShippers;
    }



    public void setWorksOrderKeys(List<WorksOrderKey> worksOrderKeys) {
        this.worksOrderKeys = worksOrderKeys;
    }

    public List<WorksOrderKey> getWorksOrderKeys() {
        return worksOrderKeys;
    }


    public void setWorksOrderLables(List<WorksOrderLable> worksOrderLables) {
        this.worksOrderLables = worksOrderLables;
    }

    public List<WorksOrderLable> getWorksOrderLables() {
        return worksOrderLables;
    }





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


    public void setManufacturingKey(ManufacturingKey manufacturingKey) {
        this.manufacturingKey = manufacturingKey == null ? null : manufacturingKey.getId();
    }

    public ManufacturingKey getManufacturingKey() {
        return manufacturingKey == null ? null : ManufacturingKey.fromId(manufacturingKey);
    }








}