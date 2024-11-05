package com.openaiproject.OpenAiProject.Response;

import java.time.Instant;

public class ImageGenerationResponseDto {
    private Long id;
    private String model;
    private String prompt;
    private int n;
    private String size;
    private Instant created;
    private String revisedPrompt;
    private String imageData; // Base64 formatında

    // Constructor, getters ve setters
    public ImageGenerationResponseDto(Long id, String model, String prompt, int n, String size, Instant created, String revisedPrompt, String imageData) {
        this.id = id;
        this.model = model;
        this.prompt = prompt;
        this.n = n;
        this.size = size;
        this.created = created;
        this.revisedPrompt = revisedPrompt;
        this.imageData = imageData;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    public int getN() { return n; }
    public void setN(int n) { this.n = n; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public Instant getCreated() { return created; }
    public void setCreated(Instant created) { this.created = created; }
    public String getRevisedPrompt() { return revisedPrompt; }
    public void setRevisedPrompt(String revisedPrompt) { this.revisedPrompt = revisedPrompt; }
    public String getImageData() { return imageData; }
    public void setImageData(String imageData) { this.imageData = imageData; }
}
