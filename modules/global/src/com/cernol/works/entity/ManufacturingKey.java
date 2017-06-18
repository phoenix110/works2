/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ManufacturingKey implements EnumClass<String> {

    Orders("O"),
    NoStock("NS"),
    LowStock("LS"),
    Stock("S"),
    StaysOvernightInTank("OV"),
    SameDayManufactured("SD");

    private String id;

    ManufacturingKey(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ManufacturingKey fromId(String id) {
        for (ManufacturingKey at : ManufacturingKey.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}