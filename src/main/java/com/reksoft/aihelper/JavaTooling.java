package com.reksoft.aihelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;

public class JavaTooling {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaTooling.class);

    @Tool(description = "Tell me release date of Java")
    public String javaReleaseDate(int version) {
        LOGGER.info("javaReleaseDate tool is invoked");
        return switch (version) {
            case 23 -> "15 November 2024";
            case 24 -> "15 March 2025";
            default -> "I don't know what to do";
        };
    }
}
