package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|code")
@Table(name = "WORKS_CATEGORY")
@Entity(name = "works$Category")
public class Category extends StandardEntity {
    private static final long serialVersionUID = 204471002615559644L;

    @Column(name = "CODE", nullable = false, unique = true, length = 10)
    protected String code;

    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    protected String description;

    @Column(name = "TAGS")
    protected String tags;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }


}