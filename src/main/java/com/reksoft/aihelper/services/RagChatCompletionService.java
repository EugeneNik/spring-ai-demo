//package com.reksoft.aihelper;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
//import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
//import org.springframework.ai.chat.memory.InMemoryChatMemory;
//import org.springframework.ai.evaluation.EvaluationRequest;
//import org.springframework.ai.evaluation.EvaluationResponse;
//import org.springframework.ai.evaluation.RelevancyEvaluator;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//public class RagChatCompletionService {
//    private final ChatClient chatClient;
//
//    public RagChatCompletionService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
//        this.chatClient = chatClientBuilder
//                .defaultAdvisors(
//                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
//                        new SimpleLoggerAdvisor(),
//                        new QuestionAnswerAdvisor(vectorStore)
//                )
//                .build();
//    }
//
//    public String tellMeAboutJava24() {
//        var userText = "Tell me about release of Java 24";
//        return chatClient.prompt(userText)
//                //...
//                .advisors(advisor -> advisor.param("chat_memory_conversation_id", "678")
//                        .param("chat_memory_response_size", 100))
//                .call()
//                .content();
//    }
//}
