/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum MixerStatus implements EnumClass<String> {

    Active("A"),
    Inactive("I"),
    Decommissioned("D");

    private String id;

    MixerStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static MixerStatus fromId(String id) {
        for (MixerStatus at : MixerStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}