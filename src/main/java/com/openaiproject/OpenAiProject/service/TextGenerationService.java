package com.openaiproject.OpenAiProject.service;


import com.openaiproject.OpenAiProject.Entities.TextGeneration;

import com.openaiproject.OpenAiProject.Repos.TextGenerationRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class TextGenerationService {

    private final TextGenerationRepository textGenerationRepository;
    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    public TextGenerationService(TextGenerationRepository textGenerationRepository, RestTemplate restTemplate) {
        this.textGenerationRepository = textGenerationRepository;
        this.restTemplate = restTemplate;
    }


    //apiye sorgu gönderme
    public TextGeneration generateText(String prompt, String model, double temperature, int maxTokens, String role, double topP) {
        // Set up request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("temperature", temperature);
        requestBody.put("top_p", topP);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("messages", List.of(
                Map.of(
                        "role", role,
                        "content", prompt
                )
        ));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Send POST request to OpenAI API
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

            // Check if response is successful
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Parse response to get the content field
                Map<String, Object> choice = ((List<Map<String, Object>>) response.getBody().get("choices")).get(0);
                String responseContent = (String) ((Map<String, Object>) choice.get("message")).get("content");

                // Create and save TextGeneration entity
                TextGeneration textGeneration = new TextGeneration();
                textGeneration.setPrompt(prompt);
                textGeneration.setModel(model);
                textGeneration.setTemperature(temperature);
                textGeneration.setTopP(topP);
                textGeneration.setMaxTokens(maxTokens);
                textGeneration.setRole(role);
                textGeneration.setResponse(responseContent);
                textGeneration.setCreatedAt(LocalDateTime.now());

                return textGenerationRepository.save(textGeneration);
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error during API call: " + e.getMessage());
        }

        return null;
    }


    //findall
    public List<TextGeneration> getAllText(){
        return textGenerationRepository.findAll();
    }

    //findbyid

    public Optional<TextGeneration> getTextById(Long id){
        return textGenerationRepository.findById(id);
    }

    //delete
    public void deleteQuestion(Long id) {
        textGenerationRepository.deleteById(id);
    }
}
