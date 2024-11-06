package com.openaiproject.OpenAiProject.Controller;


import com.openaiproject.OpenAiProject.Entities.TextGeneration;
import com.openaiproject.OpenAiProject.Request.TextGenerationRequest;
import com.openaiproject.OpenAiProject.service.TextGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gpt")
public class TextGenerationController {


    private final TextGenerationService textGenerationService;


    @Autowired
    public TextGenerationController(TextGenerationService textGenerationService) {
        this.textGenerationService = textGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<TextGeneration> generateText(@RequestBody TextGenerationRequest request) {
        TextGeneration result = textGenerationService.generateText(
                request.getPrompt(),
                request.getModel(),
                request.getTemperature(),
                request.getMaxTokens(),
                request.getRole(),
                request.getTopP()
        );

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/texts")
    public ResponseEntity<List<TextGeneration>> getAllText() {
        List<TextGeneration> textList = textGenerationService.getAllText();
        return new ResponseEntity<>(textList, HttpStatus.OK);
    }
    @GetMapping("/texts/{id}")
    public ResponseEntity<TextGeneration> getTextById(@PathVariable Long id) {
        Optional<TextGeneration> textGeneration = textGenerationService.getTextById(id);
        return textGeneration
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/texts/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        textGenerationService.deleteQuestion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
