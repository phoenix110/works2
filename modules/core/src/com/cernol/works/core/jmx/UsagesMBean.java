package com.cernol.works.core.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Manages Stock Item usage")
public interface UsagesMBean {

    @ManagedOperation(description = "Calculate usage for all Raw Materials on a specified date")
    String calculateRMUsagesOn(String onDate);

    @ManagedOperation(description = "Calculate usage for all Raw Materials since a specified date")
    String calculateRMUsagesSince(String sinceDate);

    @ManagedOperation(description = "Calculate usage for all Containers on a specified date")
    String calculateCUsagesOn(String onDate);

    @ManagedOperation(description = "Calculate usage for all Containers since a specified date")
    String calculateCUsagesSince(String sinceDate);

}
