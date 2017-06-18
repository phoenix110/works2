/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DocumentStatus implements EnumClass<String> {

    New("N"),
    Accepted("A"),
    Cancelled("C");

    private String id;

    DocumentStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DocumentStatus fromId(String id) {
        for (DocumentStatus at : DocumentStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}