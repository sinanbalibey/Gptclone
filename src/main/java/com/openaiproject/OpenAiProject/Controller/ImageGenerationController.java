package com.openaiproject.OpenAiProject.Controller;



import com.openaiproject.OpenAiProject.Entities.ImageGeneration;
import com.openaiproject.OpenAiProject.Request.ImageRequest;
import com.openaiproject.OpenAiProject.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dalle")
public class ImageGenerationController {


    private final ImageGenerationService imageGenerationService;


    @Autowired
    public ImageGenerationController(ImageGenerationService imageGenerationService) {
        this.imageGenerationService = imageGenerationService;
    }



    // 1. Resim oluşturma
    @PostMapping("/generate-image")
    public ResponseEntity<List<ImageGeneration>> generateImage(@RequestBody ImageRequest imageRequest) {
        List<ImageGeneration> generatedImages = imageGenerationService.generateImage(
                imageRequest.getModel(),
                imageRequest.getPrompt(),
                imageRequest.getN(),
                imageRequest.getSize()
        );
        return ResponseEntity.ok(generatedImages);
    }

    // 2. Tüm kayıtları getirme
    @GetMapping("/images")
    public ResponseEntity<List<ImageGeneration>> getAllImages() {
        List<ImageGeneration> images = imageGenerationService.getAllQuestions();
        return ResponseEntity.ok(images);
    }

    // 3. ID'ye göre kayıt getirme
    @GetMapping("/images/{id}")
    public ResponseEntity<ImageGeneration> getImageById(@PathVariable Long id) {
        Optional<ImageGeneration> image = imageGenerationService.getQuestionById(id);
        return image.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. Kayıt silme
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageGenerationService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
