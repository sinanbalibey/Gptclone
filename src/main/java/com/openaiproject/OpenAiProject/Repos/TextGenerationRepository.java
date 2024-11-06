package com.openaiproject.OpenAiProject.Repos;

import com.openaiproject.OpenAiProject.Entities.TextGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TextGenerationRepository extends JpaRepository<TextGeneration,Long> {

}
