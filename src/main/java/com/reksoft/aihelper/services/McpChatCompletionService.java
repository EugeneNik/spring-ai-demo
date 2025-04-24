package com.reksoft.aihelper.services;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class McpChatCompletionService {
    private final ChatClient chatClient;

    public McpChatCompletionService(ChatClient.Builder chatClientBuilder,
                                 List<McpSyncClient> mcpSyncClients) {
        this.chatClient = chatClientBuilder
                .defaultTools(new SyncMcpToolCallbackProvider(mcpSyncClients))
                .build();
    }

    public String tellMeAboutJava24() {
        var userText = "Tell me about release of Java 24";
        return chatClient.prompt(userText)
                .call()
                .content();
    }
}
