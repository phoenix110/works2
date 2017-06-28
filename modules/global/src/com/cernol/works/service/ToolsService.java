package com.cernol.works.service;

import java.time.Instant;

public interface ToolsService {
    String NAME = "works_ToolsService";

    public Instant getNow();

    public String getSystemKey(String context, String key);
}