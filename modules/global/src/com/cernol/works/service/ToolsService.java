package com.cernol.works.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface ToolsService {
    String NAME = "works_ToolsService";

    public Instant getNow();

    public Date asDate(LocalDate localDate);

    public Date asDate(LocalDateTime localDateTime);

    public LocalDate asLocalDate(Date date);

    public LocalDateTime asLocalDateTime(Date date);

    public Date beginOfDay(Date date);

    public Date endOfDay(Date date);


}