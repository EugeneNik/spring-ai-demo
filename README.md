# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/maven-plugin/build-image.html)
* [Model Context Protocol Client](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html)
* [Model Context Protocol Server](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html)
* [Mistral AI](https://docs.spring.io/spring-ai/reference/api/chat/mistralai-chat.html)

### About Spring AI

Spring AI is a project designed to streamline the development of applications that incorporate artificial intelligence functionality without unnecessary complexity. It provides abstractions that serve as the foundation for AI applications, with multiple implementations that enable easy component swapping with minimal code changes.

Key features of Spring AI include:
- Portable API support across AI providers for Chat, text-to-image, and Embedding models
- Support for major AI Model providers (Anthropic, OpenAI, Microsoft, Amazon, Google, Ollama)
- Structured Output capabilities for mapping AI responses to POJOs
- Vector Database integration for Retrieval Augmented Generation (RAG)
- Tools/Function Calling for real-time information access and actions
- Observability for insights into AI operations
- Spring Boot Auto Configuration and Starters

### Spring AI Demo Cases

This project demonstrates various capabilities of Spring AI through different implementations:

#### Case 1: Simple Chat (`commandLineRunner_task1`)
- **Implementation**: `SimpleChatService`
- **Description**: Basic chat completion with temperature control (1.0) for creative responses
- **Features**: Uses `ChatOptions` to configure model behavior
- **Technical Details**:
  ```java
  return chatClient.prompt(userText)
          .options(ChatOptions.builder().temperature(1.0).build())
          .call()
          .content();
  ```
- **Note**: This is the only enabled runner in the current configuration

#### Case 2: Memory-Less Simple Chat (`commandLineRunner_task2`)
- **Implementation**: `MemoryLessSimpleChatService`
- **Description**: Demonstrates stateless conversation where each prompt is independent
- **Features**: Shows limitations when asking follow-up questions ("And what about 25?") without context
- **Technical Details**: Sends two separate prompts without maintaining state between them:
  ```java
  public String tellMeAboutJava24() {
      return chatClient.prompt("Tell me about release of Java 24").call().content();
  }
  
  public String tellMeAboutJava25() {
      return chatClient.prompt("And what about 25?").call().content();
  }
  ```

#### Case 3: Chat with Memory (`commandLineRunner_task3`)
- **Implementation**: `MemorableChatService`
- **Description**: Conversation with memory to maintain context between prompts
- **Features**: Uses `MessageChatMemoryAdvisor` with `InMemoryChatMemory` to store conversation history
- **Technical Details**:
  ```java
  public MemorableChatService(ChatClient.Builder chatClientBuilder) {
      var preparedBuilder = chatClientBuilder
              .defaultAdvisors(
                      new MessageChatMemoryAdvisor(new InMemoryChatMemory())
              );
      super(preparedBuilder);
  }
  ```
- **Requirements**: Spring AI Memory dependencies

#### Case 4: Self-Evaluated Chat (`commandLineRunner_task4`)
- **Implementation**: `SelfEvaluatedChatService`
- **Description**: Evaluates AI response relevancy before returning to the user
- **Features**: Uses `RelevancyEvaluator` to assess response quality
- **Technical Details**:
  ```java
  private String parseEvaluated(String chatResponse, String userText) {
      var evaluator = new RelevancyEvaluator(chatClient.mutate());
      var result = evaluator.evaluate(new EvaluationRequest(userText, Collections.emptyList(), chatResponse));
      if (result.isPass()) {
          return chatResponse;
      }
      return "I can't. " + result.getFeedback();
  }
  ```
- **Requirements**: Spring AI Evaluation dependencies

#### Case 5: Tool-Enabled Chat (`commandLineRunner_task5`)
- **Implementation**: `ToolChatCompletionService`
- **Description**: Demonstrates function/tool calling capabilities
- **Features**: Integrates `JavaTooling` with annotated tools that the AI can invoke
- **Technical Details**:
  ```java
  public class JavaTooling {
      @Tool(description = "Tell me release date of Java")
      public String javaReleaseDate(int version) {
          return switch (version) {
              case 23 -> "15 November 2024";
              case 24 -> "15 March 2025";
              default -> "I don't know what to do";
          };
      }
  }
  ```
- **Usage**:
  ```java
  return chatClient.prompt("Tell me about release of Java 24")
          .tools(new JavaTooling())
          .call().content();
  ```

#### Case 6: Model Context Protocol (MCP) Integration (`commandLineRunner_task6`)
- **Implementation**: `McpChatCompletionService`
- **Description**: Integrates with Model Context Protocol for standardized model interactions
- **Features**: Uses `SyncMcpToolCallbackProvider` with MCP clients
- **Technical Details**:
  ```java
  public McpChatCompletionService(ChatClient.Builder chatClientBuilder,
                              List<McpSyncClient> mcpSyncClients) {
      this.chatClient = chatClientBuilder
              .defaultTools(new SyncMcpToolCallbackProvider(mcpSyncClients))
              .build();
  }
  ```
- **Requirements**: Spring AI MCP dependencies and configured MCP clients

#### Case 7: Structured Output (`commandLineRunner_task7`)
- **Implementation**: `StructuredOutputChatCompletionService`
- **Description**: Maps AI responses directly to Java objects
- **Features**: Uses the `.entity()` method to automatically convert responses to structured data
- **Technical Details**:
  ```java
  private record ReleaseWithDate(String name, LocalDate date) {}
  
  public String tellMeAboutJava24() {
      var release = chatClient.prompt("Tell me about release of Java 24")
              .tools(new JavaTooling())
              .call()
              .entity(ReleaseWithDate.class);
  
      return release.name() + " " + release.date();
  }
  ```

#### Case 8: Low-Level Structured Output (`commandLineRunner_task8`)
- **Implementation**: `StructuredOutputLowLevelChatCompletionService`
- **Description**: Manual structured output mapping with format templates
- **Features**: Uses `BeanOutputConverter` and `PromptTemplate` for explicit control
- **Technical Details**:
  ```java
  BeanOutputConverter<ReleaseWithDate> beanOutputConverter = new BeanOutputConverter<>(ReleaseWithDate.class);
  String format = beanOutputConverter.getFormat();
  
  var generation = chatClient
          .prompt(new PromptTemplate(userText, Map.of("format", format)).create())
          .tools(new JavaTooling())
          .call().chatResponse().getResult();
  
  var release = beanOutputConverter.convert(generation.getOutput().getText());
  ```

### How to Enable Different Demo Cases

By default, only Case 1 (Simple Chat) is enabled. To experiment with other cases:

1. Open `AiHelperApplication.java`
2. Comment out the current active case (task1)
3. Uncomment the case you want to run (e.g., task2, task3, etc.)
4. Run the application

Note that some cases may require additional dependencies or configuration based on their complexity.

### Additional Resources

For more information and examples related to Spring AI, refer to:
- [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring AI GitHub Repository](https://github.com/spring-projects/spring-ai)
- [Spring AI Samples](https://docs.spring.io/spring-ai/reference/getting-started.html#spring-ai-samples)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

