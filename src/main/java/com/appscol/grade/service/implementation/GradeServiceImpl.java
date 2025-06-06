package com.appscol.grade.service.implementation;

import com.appscol.grade.factory.GradeFactory;
import com.appscol.grade.persistence.Repositories.GradeRepository;
import com.appscol.grade.persistence.entities.GradeEntity;
import com.appscol.grade.presentation.dto.GradeDto;
import com.appscol.grade.presentation.payload.GradePayload;
import com.appscol.grade.service.interfaces.IGradeService;
import com.appscol.helpers.exception.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements IGradeService {

    private final ModelMapper modelMapper;
    private final GradeRepository gradeRepository;
    private final GradeFactory gradeFactory;

    @Override
    @Transactional()
    public void createGrade(GradePayload gradePayload) {
        if(gradeRepository.existsByGrade(gradePayload.getGrade())){
            throw new ConflictException("Ya existe un grado con el nombre: " + gradePayload.getGrade());
        }

        GradeEntity gradeEntity = modelMapper.map(gradePayload, GradeEntity.class);
        gradeRepository.save(gradeEntity);
    }

    @Override
    @Transactional()
    public void updateGrade(Long id, GradePayload gradePayload) {
        GradeEntity gradeEntity = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));

        modelMapper.map(gradePayload, gradeEntity);
        gradeRepository.save(gradeEntity);
    }

    @Override
    @Transactional()
    public void deleteGrade(Long id) {
        GradeEntity gradeEntity = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
        gradeRepository.delete(gradeEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDto findById(Long id) {
        return gradeRepository.findById(id)
                .map(gradeFactory::gradeDto)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GradeDto> findAll(Pageable pageable) {
        return gradeRepository.findAll(pageable).map(gradeFactory::gradeDto);
    }
}
