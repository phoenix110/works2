package com.cernol.works.core.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.Date;

@ManagedResource(description = "Manages price list updates")
public interface PriceListsMBean {

    @ManagedOperation
    String calculatePriceListsOn(Date onDate);

}
