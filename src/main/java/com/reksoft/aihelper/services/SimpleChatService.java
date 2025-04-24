package com.reksoft.aihelper.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;


@Service
public class SimpleChatService extends AbstractStageService {
    public SimpleChatService(ChatClient.Builder chatClient) {
        super(chatClient);
    }

    public String tellMeAboutJava24() {
        var userText = "Tell me about release of Java 24";
        return chatClient.prompt(userText)
                .options(ChatOptions.builder().temperature(1.0).build())
                .call()
                .content();
    }
}
