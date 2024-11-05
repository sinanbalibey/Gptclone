package com.openaiproject.OpenAiProject.Repos;

import com.openaiproject.OpenAiProject.Entities.ImageGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageGenerationRepository extends JpaRepository<ImageGeneration,Long> {
}
