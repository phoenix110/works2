package com.cernol.works.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.util.UUID;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|description")
@MetaClass(name = "works$ProblemList")
public class ProblemList extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = 5918175724361915566L;

    @MetaProperty
    protected UUID parent;

    @MetaProperty
    protected String description;

    public void setParent(UUID parent) {
        this.parent = parent;
    }

    public UUID getParent() {
        return parent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}