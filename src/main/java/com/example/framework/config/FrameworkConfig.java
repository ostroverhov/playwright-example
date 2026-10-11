package com.example.framework.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FrameworkConfig {
    private String baseUrl;
    private String browser;
    private String channel;
    private boolean headless;
    private double timeoutMs;
    private String locale;
    private List<String> args = new ArrayList<>();
}
