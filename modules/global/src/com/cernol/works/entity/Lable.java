/*
 * Copyright (c) 2017 CubeIT
 */
package com.cernol.works.entity;

import javax.persistence.*;
import java.math.BigDecimal;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|description")
@PrimaryKeyJoinColumn(name = "STOCK_ITEM_ID", referencedColumnName = "ID")
@Table(name = "WORKS_LABLE")
@Entity(name = "works$Lable")
public class Lable extends StockItem {
    private static final long serialVersionUID = -2301445352972513316L;


    @Column(name = "SIZE_X")
    protected Integer sizeX;

    @Column(name = "SIZE_Y")
    protected Integer sizeY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_FILE_ID")
    protected FileDescriptor imageFile;

    public void setSizeX(Integer sizeX) {
        this.sizeX = sizeX;
    }

    public Integer getSizeX() {
        return sizeX;
    }

    public void setSizeY(Integer sizeY) {
        this.sizeY = sizeY;
    }

    public Integer getSizeY() {
        return sizeY;
    }


    public void setImageFile(FileDescriptor imageFile) {
        this.imageFile = imageFile;
    }

    public FileDescriptor getImageFile() {
        return imageFile;
    }



}