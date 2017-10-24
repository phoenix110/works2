package com.cernol.works.core.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.Date;

@ManagedResource(description = "Manages Calendar entries")
public interface CalendarsMBean {

    @ManagedOperation(description = "Create or Update entries since a specified date")
    String createOrUpdateCalendarsSince(Date sinceDate, Date untilDate);
}
