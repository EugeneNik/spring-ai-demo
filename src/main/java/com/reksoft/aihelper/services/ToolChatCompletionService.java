package com.reksoft.aihelper.services;

import com.reksoft.aihelper.JavaTooling;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ToolChatCompletionService {
    private final ChatClient chatClient;

    public ToolChatCompletionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String tellMeAboutJava24() {
        return chatClient.prompt("Tell me about release of Java 24")
                .tools(new JavaTooling())
                .call().content();
    }
}
