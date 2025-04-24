package com.reksoft.aihelper.services;

import com.reksoft.aihelper.JavaTooling;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class StructuredOutputLowLevelChatCompletionService {
    private final ChatClient chatClient;

    public StructuredOutputLowLevelChatCompletionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    private record ReleaseWithDate(String name, LocalDate date) { }

    public String tellMeAboutJava24() {
        var userText = """
                Tell me about release of Java 24
                {format}
                """;

        BeanOutputConverter<ReleaseWithDate> beanOutputConverter = new BeanOutputConverter<>(ReleaseWithDate.class);
        String format = beanOutputConverter.getFormat();

        var generation = chatClient
                .prompt(new PromptTemplate(userText, Map.of("format", format)).create())
                .tools(new JavaTooling())
                .call().chatResponse().getResult();

        var release = beanOutputConverter.convert(generation.getOutput().getText());

        return release.name() + " " + release.date();
    }
}
