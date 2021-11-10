package com.pokechess.server.datasources.loader.dto.actions;

import org.springframework.lang.Nullable;

import java.util.List;

public class TriggerDTO {
    private String action;
    private List<String> targets;
    private String status;
    private String duration;

    @Nullable
    public String getAction() {
        return action;
    }

    @Nullable
    public List<String> getTargets() {
        return targets;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getDuration() {
        return duration;
    }
}
