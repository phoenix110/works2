/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "WORKS_MIXER")
@Entity(name = "works$Mixer")
public class Mixer extends StandardEntity {
    private static final long serialVersionUID = 1615758571809399486L;

    @Column(name = "NAME", nullable = false, unique = true, length = 25)
    protected String name;

    @Column(name = "MIN_LOAD", nullable = false)
    protected Integer minLoad;

    @Column(name = "MAX_LOAD", nullable = false)
    protected Integer maxLoad;

    @Column(name = "UNIT", nullable = false)
    protected String unit;

    @NotNull
    @Column(name = "CURRENT_STATUS", nullable = false)
    protected String currentStatus;

    @Column(name = "PHYSICAL_FORM", nullable = false)
    protected String physicalForm;

    public PhysicalForm getPhysicalForm() {
        return physicalForm == null ? null : PhysicalForm.fromId(physicalForm);
    }

    public void setPhysicalForm(PhysicalForm physicalForm) {
        this.physicalForm = physicalForm == null ? null : physicalForm.getId();
    }


    public Unit getUnit() {
        return unit == null ? null : Unit.fromId(unit);
    }

    public void setUnit(Unit unit) {
        this.unit = unit == null ? null : unit.getId();
    }


    public MixerStatus getCurrentStatus() {
        return currentStatus == null ? null : MixerStatus.fromId(currentStatus);
    }

    public void setCurrentStatus(MixerStatus currentStatus) {
        this.currentStatus = currentStatus == null ? null : currentStatus.getId();
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMinLoad(Integer minLoad) {
        this.minLoad = minLoad;
    }

    public Integer getMinLoad() {
        return minLoad;
    }

    public void setMaxLoad(Integer maxLoad) {
        this.maxLoad = maxLoad;
    }

    public Integer getMaxLoad() {
        return maxLoad;
    }


}