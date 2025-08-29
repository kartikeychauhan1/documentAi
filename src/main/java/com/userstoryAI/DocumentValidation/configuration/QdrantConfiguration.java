package com.userstoryAI.DocumentValidation.configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QdrantConfiguration {

	@Value("${vectorstore.qdrant.host}")
	private String hostname;
	@Value("${vectorstore.qdrant.port}")
	private int port;
	@Value("${vectorstore.qdrant.api-key}")
	private String apiKey;
	@Value("${vectorstore.qdrant.use-tls}")
	private boolean istls;
	@Value("${vectorstore.qdrant.initialize-schema}")
	private boolean initializeSchema;
	@Value("${vectorstore.qdrant.collection-name}")
	private String collectionName;

	@Bean
	public QdrantClient qdrantClient() {
		QdrantGrpcClient.Builder grpcClientBuilder =
				QdrantGrpcClient.newBuilder(hostname
						,port
						, istls);
		grpcClientBuilder.withApiKey(apiKey);

		return new QdrantClient(grpcClientBuilder.build());
	}

	@Bean
	public EmbeddingStore<TextSegment> embeddingStore(QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
		return QdrantEmbeddingStore.builder()
				.client(qdrantClient)
				.collectionName(collectionName)
				.build();
	}


}
