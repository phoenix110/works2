/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.*;

import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import java.math.BigDecimal;

import com.haulmont.chile.core.annotations.NamePattern;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import com.haulmont.cuba.core.entity.FileDescriptor;

@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_PRODUCT")
@NamePattern("%s|code")
@Entity(name = "works$Product")
public class Product extends StockItem {
    private static final long serialVersionUID = 7981031785286293876L;

    @Column(name = "SPECIFIC_GRAVITY", nullable = false, precision = 19, scale = 4)
    protected BigDecimal specificGravity;

    @Column(name = "APPLY_OVERHEAD", nullable = false)
    protected Boolean applyOverhead = false;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CATEGORY_ID")
    protected Category category;

    @Column(name = "IS_CORROSIVE")
    protected Boolean isCorrosive;

    @Column(name = "IS_FLAMMABLE")
    protected Boolean isFlammable;

    @Column(name = "IS_POISONOUS")
    protected Boolean isPoisonous;

    @Column(name = "KEEP_AWAY")
    protected Boolean keepAway;

    @Column(name = "PHYSICAL_FORM", nullable = false)
    protected String physicalForm;

    @OrderBy("sequenceNo")
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "product")
    @Size(min = 1)
    @Valid
    protected List<Formula> formula;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPEC_FILE_ID")
    protected FileDescriptor specFile;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "product")
    protected List<ProductContainer> containers;

    @Column(name = "IS_FINAL")
    protected Boolean isFinal;

    public void setKeepAway(Boolean keepAway) {
        this.keepAway = keepAway;
    }

    public Boolean getKeepAway() {
        return keepAway;
    }


    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    public Boolean getIsFinal() {
        return isFinal;
    }


    public void setContainers(List<ProductContainer> containers) {
        this.containers = containers;
    }

    public List<ProductContainer> getContainers() {
        return containers;
    }


    public void setSpecFile(FileDescriptor specFile) {
        this.specFile = specFile;
    }

    public FileDescriptor getSpecFile() {
        return specFile;
    }


    public PhysicalForm getPhysicalForm() {
        return physicalForm == null ? null : PhysicalForm.fromId(physicalForm);
    }

    public void setPhysicalForm(PhysicalForm physicalForm) {
        this.physicalForm = physicalForm == null ? null : physicalForm.getId();
    }



    public List<Formula> getFormula() {
        return formula;
    }

    public void setFormula(List<Formula> formula) {
        this.formula = formula;
    }


    public void setIsCorrosive(Boolean isCorrosive) {
        this.isCorrosive = isCorrosive;
    }

    public Boolean getIsCorrosive() {
        return isCorrosive;
    }

    public void setIsFlammable(Boolean isFlammable) {
        this.isFlammable = isFlammable;
    }

    public Boolean getIsFlammable() {
        return isFlammable;
    }

    public void setIsPoisonous(Boolean isPoisonous) {
        this.isPoisonous = isPoisonous;
    }

    public Boolean getIsPoisonous() {
        return isPoisonous;
    }



    public void setSpecificGravity(BigDecimal specificGravity) {
        this.specificGravity = specificGravity;
    }

    public BigDecimal getSpecificGravity() {
        return specificGravity;
    }

    public void setApplyOverhead(Boolean applyOverhead) {
        this.applyOverhead = applyOverhead;
    }

    public Boolean getApplyOverhead() {
        return applyOverhead;
    }



    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }


}