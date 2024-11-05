package com.openaiproject.OpenAiProject.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ImageGenerationResponse {
    private int created;

    @JsonProperty("data")
    private List<ImageData> data;

    // Getters and Setters

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public List<ImageData> getData() {
        return data;
    }

    public void setData(List<ImageData> data) {
        this.data = data;
    }

    public static class ImageData {
        private String revisedPrompt;
        private String url;

        // Getters and Setters

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
}
