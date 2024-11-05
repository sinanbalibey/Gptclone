package com.openaiproject.OpenAiProject.Entities;


import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "image_generations")
public class ImageGeneration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String prompt;

    @Column(nullable = false)
    private int n;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private Instant created;

    @Column(name = "revised_prompt", nullable = false)
    private String revisedPrompt;

    @Column(nullable = false, length = 1024)
    private String url;

    // Constructors, getters, and setters
    public ImageGeneration() {}

    public ImageGeneration(String model, String prompt, int n, String size, Instant created, String revisedPrompt, String url) {
        this.model = model;
        this.prompt = prompt;
        this.n = n;
        this.size = size;
        this.created = created;
        this.revisedPrompt = revisedPrompt;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getRevisedPrompt() {
        return revisedPrompt;
    }

    public void setRevisedPrompt(String revisedPrompt) {
        this.revisedPrompt = revisedPrompt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
