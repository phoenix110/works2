package com.cernol.works.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service(ToolsService.NAME)
public class ToolsServiceBean implements ToolsService {


    @Override
    public Instant getNow() {
        LocalDateTime ldt = LocalDateTime.now();
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();

        return instant;
    }
}