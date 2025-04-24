package com.reksoft.aihelper.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class MemorableChatService extends AbstractStageService {
    public MemorableChatService(ChatClient.Builder chatClientBuilder) {
        var preparedBuilder = chatClientBuilder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                );
        super(preparedBuilder);
    }

    public String tellMeAboutJava24() {
        return chatClient.prompt("Tell me about release of Java 24")
                .advisors(this::link)
                .call().content();
    }

    public String tellMeAboutJava25() {
        return chatClient.prompt("And what about 25?")
                .advisors(this::link)
                .call().content();
    }

    private ChatClient.AdvisorSpec link(ChatClient.AdvisorSpec advisorSpec) {
        return advisorSpec.param("chat_memory_conversation_id", "678")
                .param("chat_memory_response_size", 100);
    }
}
