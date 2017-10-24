package com.cernol.works.core.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Manages Stock Item usage")
public interface UsagesMBean {

    @ManagedOperation(description = "Calculate usage for all Stock Items on a specified date")
    String calculateUsagesOn(String onDate);

    @ManagedOperation(description = "Calculate usage for all Stock Items since a specified date")
    String calculateUsagesSince(String sinceDate);

}
