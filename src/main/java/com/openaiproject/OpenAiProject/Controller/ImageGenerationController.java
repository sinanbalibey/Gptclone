package com.openaiproject.OpenAiProject.Controller;



import com.openaiproject.OpenAiProject.Entities.ImageGeneration;
import com.openaiproject.OpenAiProject.Request.ImageRequest;
import com.openaiproject.OpenAiProject.Response.ImageGenerationResponseDto;
import com.openaiproject.OpenAiProject.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Base64;

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
    public ResponseEntity<List<ImageGenerationResponseDto>> generateImage(@RequestBody ImageRequest imageRequest) {
        List<ImageGeneration> generatedImages = imageGenerationService.generateImage(
                imageRequest.getModel(),
                imageRequest.getPrompt(),
                imageRequest.getN(),
                imageRequest.getSize()
        );

        // ImageGeneration nesnelerini ImageGenerationResponseDto nesnelerine dönüştür
        List<ImageGenerationResponseDto> responseDtos = generatedImages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    // 2. Tüm kayıtları getirme
    @GetMapping("/images")
    public ResponseEntity<List<ImageGenerationResponseDto>> getAllImages() {
        List<ImageGeneration> images = imageGenerationService.getAllQuestions();

        List<ImageGenerationResponseDto> responseDtos = images.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    // 3. ID'ye göre kayıt getirme
    @GetMapping("/images/{id}")
    public ResponseEntity<ImageGenerationResponseDto> getImageById(@PathVariable Long id) {
        Optional<ImageGeneration> image = imageGenerationService.getQuestionById(id);
        return image.map(img -> ResponseEntity.ok(convertToDto(img)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. Kayıt silme
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageGenerationService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // Yardımcı metod: ImageGeneration nesnesini ImageGenerationResponseDto'ya dönüştür
    private ImageGenerationResponseDto convertToDto(ImageGeneration imageGeneration) {
        // imageData null mu kontrol et, eğer null ise boş bir string kullan
        String base64Image = (imageGeneration.getImageData() != null) ?
                Base64.getEncoder().encodeToString(imageGeneration.getImageData()) : "";

        return new ImageGenerationResponseDto(
                imageGeneration.getId(),
                imageGeneration.getModel(),
                imageGeneration.getPrompt(),
                imageGeneration.getN(),
                imageGeneration.getSize(),
                imageGeneration.getCreated(),
                imageGeneration.getRevisedPrompt(),
                base64Image
        );
    }
}
