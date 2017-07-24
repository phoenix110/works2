/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;
import java.math.BigDecimal;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_RAW_MATERIAL")
@NamePattern("%s %s|code,description")
@Entity(name = "works$RawMaterial")
public class RawMaterial extends StockItem {
    private static final long serialVersionUID = -4487387176102137705L;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPEC_FILE_ID")
    protected FileDescriptor specFile;

    public void setSpecFile(FileDescriptor specFile) {
        this.specFile = specFile;
    }

    public FileDescriptor getSpecFile() {
        return specFile;
    }






}