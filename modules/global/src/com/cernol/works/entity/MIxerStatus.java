/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum MIxerStatus implements EnumClass<String> {

    Active("A"),
    Inactive("I"),
    Decommissioned("D");

    private String id;

    MIxerStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static MIxerStatus fromId(String id) {
        for (MIxerStatus at : MIxerStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}