package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "WORKS_INSTRUCTION")
@Entity(name = "works$Instruction")
public class Instruction extends StandardEntity {
    private static final long serialVersionUID = -6004458231832612146L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}