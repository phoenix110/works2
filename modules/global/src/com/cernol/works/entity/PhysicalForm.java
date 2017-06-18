/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PhysicalForm implements EnumClass<String> {

    Liquid("L"),
    Powder("P"),
    Gel("G"),
    Undefined("U");

    private String id;

    PhysicalForm(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PhysicalForm fromId(String id) {
        for (PhysicalForm at : PhysicalForm.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}