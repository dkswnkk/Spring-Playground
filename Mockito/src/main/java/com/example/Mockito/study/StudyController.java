package com.example.Mockito.study;

import com.example.Mockito.domain.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {
    final StudyRepository repository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) throws IllegalAccessException {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Study not found for '" + id + "'"));
    }

    @PostMapping("/study")
    public Study createStudy(@RequestBody Study study) {
        return repository.save(study);
    }
}
