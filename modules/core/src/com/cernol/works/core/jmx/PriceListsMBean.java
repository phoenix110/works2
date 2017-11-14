package com.cernol.works.core.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.Date;

@ManagedResource(description = "Manages price list updates")
public interface PriceListsMBean {

    @ManagedOperation(description = "Calculate prices as at a certain date")
    String calculatePriceListsOn(Date onDate);

    @ManagedOperation(description = "Calculate prices for previous day")
    String calculateYesterdayPriceLists();

}
