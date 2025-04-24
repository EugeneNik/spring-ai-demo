package com.reksoft.aihelper.services;

import com.reksoft.aihelper.JavaTooling;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StructuredOutputChatCompletionService {
    private final ChatClient chatClient;

    public StructuredOutputChatCompletionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    private record ReleaseWithDate(String name, LocalDate date) {}

    public String tellMeAboutJava24() {
        var release = chatClient.prompt("Tell me about release of Java 24")
                .tools(new JavaTooling())
                .call()
                .entity(ReleaseWithDate.class);

        return release.name() + " " + release.date();
    }
}
