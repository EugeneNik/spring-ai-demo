package com.reksoft.aihelper.services;

import org.springframework.ai.chat.client.ChatClient;

public class AbstractStageService {
    protected final ChatClient chatClient;

    public AbstractStageService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
}
