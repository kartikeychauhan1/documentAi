package com.userstoryAI.DocumentValidation.configuration;

import java.time.Duration;
import java.util.Map;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "documentvalidationagent.ai.aiType", havingValue = "OLLAMA")
public class OllamaChatModelConfiguration extends ChatModelConfiguration {

	@Bean
	public ChatModel getModel() {
		return OllamaChatModel
				.builder()
				.modelName(modelName)
				.customHeaders(Map.of("Authorization", "Bearer " + apiKey))
				.baseUrl(apiUrl)
				.temperature(temperature)
				.timeout(Duration.ofMinutes(timeout))
				.build();
	}

	//    @Bean
	//    public EmbeddingModel embeddingModel() {
	//        return  new OllamaEmbeddingModel("http://localhost:11434", "nomic-embed-text");
	//    }
}
