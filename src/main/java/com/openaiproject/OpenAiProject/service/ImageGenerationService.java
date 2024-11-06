package com.openaiproject.OpenAiProject.service;


import com.openaiproject.OpenAiProject.Entities.ImageGeneration;
import com.openaiproject.OpenAiProject.Repos.ImageGenerationRepository;
import com.openaiproject.OpenAiProject.Response.ImageGenerationResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import org.springframework.http.HttpHeaders;


import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageGenerationService {

    private final ImageGenerationRepository imageGenerationRepository;
    private final RestTemplate restTemplate;

    private final String apiUrl = "https://api.openai.com/v1/images/generations";

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;


    public ImageGenerationService(ImageGenerationRepository imageGenerationRepository) {
        this.imageGenerationRepository = imageGenerationRepository;
        this.restTemplate = new RestTemplate();
    }

    // 1. API Sorgusu Atma (Image generation)
    public List<ImageGeneration> generateImage(String model, String prompt, int n, String size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String jsonBody = String.format(
                "{\"model\": \"%s\", \"prompt\": \"%s\", \"n\": %d, \"size\": \"%s\"}",
                model, prompt, n, size
        );

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<ImageGenerationResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                ImageGenerationResponse.class
        );

        List<ImageGeneration> generatedImages = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ImageGenerationResponse apiResponse = response.getBody();

            if (apiResponse.getData() != null && !apiResponse.getData().isEmpty()) {
                for (ImageGenerationResponse.ImageData imageData : apiResponse.getData()) {
                    ImageGeneration imageGeneration = new ImageGeneration();
                    imageGeneration.setModel(model);
                    imageGeneration.setPrompt(prompt);
                    imageGeneration.setN(n);
                    imageGeneration.setSize(size);
                    imageGeneration.setCreated(Instant.ofEpochSecond(apiResponse.getCreated()));
                    imageGeneration.setRevisedPrompt(imageData.getRevisedPrompt() != null ? imageData.getRevisedPrompt() : "Default revised prompt");
                    imageGeneration.setUrl(imageData.getUrl());

                    // Resmi indirip byte array olarak kaydedin
                    try {
                        byte[] imageBytes = downloadImageBytes(imageData.getUrl());
                        imageGeneration.setImageData(imageBytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    imageGenerationRepository.save(imageGeneration);
                    generatedImages.add(imageGeneration);
                }
            }
        }

        return generatedImages;
    }

    // Görüntüyü URL üzerinden indirip byte array'e dönüştüren metod
    private byte[] downloadImageBytes(String imageUrl) throws Exception {
        try (InputStream in = new URL(imageUrl).openStream()) {
            return in.readAllBytes();
        }
    }

    // 2. Tüm Soruları Getirme
    public List<ImageGeneration> getAllQuestions() {
        return imageGenerationRepository.findAll();
    }

    // 3. ID'ye Göre Soru Getirme
    public Optional<ImageGeneration> getQuestionById(Long id) {
        return imageGenerationRepository.findById(id);
    }

    // 4. Soru Silme
    public void deleteQuestion(Long id) {
        if (imageGenerationRepository.existsById(id)) {
            imageGenerationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Soru bulunamadı.");
        }
    }

}
