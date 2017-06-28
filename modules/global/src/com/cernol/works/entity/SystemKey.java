package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.UniqueConstraint;

@NamePattern("%s %s|context,key")
@Table(name = "WORKS_SYSTEM_KEY", uniqueConstraints = {
    @UniqueConstraint(name = "IDX_WORKS_SYSTEM_KEY_UNQ", columnNames = {"CONTEXT", "KEY"})
})
@Entity(name = "works$SystemKey")
public class SystemKey extends StandardEntity {
    private static final long serialVersionUID = -5197076303979038337L;

    @Column(name = "CONTEXT", nullable = false)
    protected String context;

    @Column(name = "KEY_", nullable = false)
    protected String key;

    @Column(name = "VALUE_")
    protected String value;

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}