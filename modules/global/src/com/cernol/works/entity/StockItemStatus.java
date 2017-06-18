/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum StockItemStatus implements EnumClass<String> {

    Active("A"),
    Discontinued("D");

    private String id;

    StockItemStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static StockItemStatus fromId(String id) {
        for (StockItemStatus at : StockItemStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}