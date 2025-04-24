package com.reksoft.aihelper;

import com.reksoft.aihelper.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiHelperApplication {
    private static final Logger logger = LoggerFactory.getLogger(AiHelperApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AiHelperApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner_task1(SimpleChatService chatCompletionService,
                                                     ConfigurableApplicationContext ctx) {
        return args -> {
            logger.info(chatCompletionService.tellMeAboutJava24());
            ctx.close();
        };
    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner_task2(MemoryLessSimpleChatService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            logger.info(chatCompletionService.tellMeAboutJava25());
//            ctx.close();
//        };
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner_task3(MemorableChatService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            logger.info(chatCompletionService.tellMeAboutJava25());
//            ctx.close();
//        };
//    }
//    @Bean
//    public CommandLineRunner commandLineRunner_task4(SelfEvaluatedChatService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            ctx.close();
//        };
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner_task5(ToolChatCompletionService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            ctx.close();
//        };
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner_task6(McpChatCompletionService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            ctx.close();
//        };
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner_task7(StructuredOutputChatCompletionService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            ctx.close();
//        };
//    }
//
//    @Bean
//    public CommandLineRunner commandLineRunner_task8(StructuredOutputLowLevelChatCompletionService chatCompletionService,
//                                                     ConfigurableApplicationContext ctx) {
//        return args -> {
//            logger.info(chatCompletionService.tellMeAboutJava24());
//            ctx.close();
//        };
//    }
}
