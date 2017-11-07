package com.cernol.works.core.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Actions that normally would only be needed once")
public interface OneTimeMBean {

    @ManagedOperation(description = "Update default labels for all product containers")
    String updateAllDefaultLabels();
}
