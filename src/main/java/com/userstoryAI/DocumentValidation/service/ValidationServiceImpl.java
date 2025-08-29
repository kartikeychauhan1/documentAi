package com.userstoryAI.DocumentValidation.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;

import com.userstoryAI.DocumentValidation.fileReader.TextReader;
import com.userstoryAI.DocumentValidation.model.ExtractText;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ValidationServiceImpl implements ValidationService {
//	VectorStore vectorStore;
//	ValidationServiceImpl(VectorStore vectorStore) {
//		this.vectorStore = vectorStore;
//	}
	Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);
	private TextReader textReader;
	private ChatModel chatModel;
	ValidationServiceImpl(TextReader textReader,ChatModel chatModel ) {
		this.textReader = textReader;
		this.chatModel = chatModel;
	}

	@Override
	public boolean validateContent(String value) throws FileNotFoundException {
		URL resource = getClass().getResource("/docs/DORA.pdf");
		Path path = new File(resource.getPath()).toPath();
		ExtractText extractText = textReader.readRequirements(path.toFile().getAbsolutePath());
		UserMessage userMessage = UserMessage.from(extractText.content());
		String response = chatModel.chat(userMessage).aiMessage().text();
		logger.info(response);
//		List<Document> documents = List.of(new Document("test"));
//		vectorStore.add(documents);
		return false;
	}
}
