package com.reksoft.aihelper.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class MemoryLessSimpleChatService extends AbstractStageService {
    public MemoryLessSimpleChatService(ChatClient.Builder chatClient) {
        super(chatClient);
    }

    public String tellMeAboutJava24() {
        return chatClient.prompt("Tell me about release of Java 24")
                .call().content();
    }

    public String tellMeAboutJava25() {
        return chatClient.prompt("And what about 25?")
                .call().content();
    }
}
