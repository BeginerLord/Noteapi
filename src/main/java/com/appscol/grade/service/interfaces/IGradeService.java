package com.appscol.grade.service.interfaces;

import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.grade.presentation.dto.GradeDto;
import com.appscol.grade.presentation.payload.GradePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGradeService {
    void createGrade(GradePayload gradePayload);
    void updateGrade(Long id, GradePayload gradePayload);
    void deleteGrade(Long id);
    GradeDto findById(Long id);
    //Optional<GradeEntity> findByGrade(String grade);
    Page<GradeDto> findAll(Pageable pageable);
}
