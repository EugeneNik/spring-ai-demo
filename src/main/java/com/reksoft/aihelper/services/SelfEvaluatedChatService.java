package com.reksoft.aihelper.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SelfEvaluatedChatService extends AbstractStageService {

    public SelfEvaluatedChatService(ChatClient.Builder chatClient) {
        super(chatClient);
    }

    public String tellMeAboutJava24() {
        var userText = "Tell me about release of Java 24";
        var chatResponse = chatClient.prompt(userText)
                .call().content();
        return parseEvaluated(chatResponse, userText);
    }

    private String parseEvaluated(String chatResponse, String userText) {
        var evaluator = new RelevancyEvaluator(chatClient.mutate());
        var result = evaluator.evaluate(new EvaluationRequest(userText, Collections.emptyList(), chatResponse));
        if (result.isPass()) {
            return chatResponse;
        }
        return "I can't. " + result.getFeedback();
    }
}
