package com.appscol.subject.service.implementation;

import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.professor.persistence.repositories.ProfessorRepository;
import com.appscol.subject.factory.SubjectFactory;
import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.subject.persistence.repositories.SubjectRepository;
import com.appscol.subject.presentation.dto.SubjectDto;
import com.appscol.subject.presentation.payload.SubjectPayload;
import com.appscol.subject.service.interfaces.ISubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements ISubjectService {
    private final ModelMapper modelMapper;
    private final SubjectRepository subjectRepository;
    private final SubjectFactory factory;
    private final ProfessorRepository professorRepository;

    @Override
    @Transactional
    public void save(SubjectPayload payload) {
        ProfessorEntity professor = professorRepository.findById(payload.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + payload.getProfessorId()));

      //  List<GradeEntity> grades = gradeRepository.findAllById(payload.getGradeIds());

        SubjectEntity subject = SubjectEntity.builder()
                .subjectName(payload.getSubjectName().trim())
                .professorEntity(professor)
               //.gradeEntities(grades)
                .build();

        subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void update(SubjectPayload payload, Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + id));

        ProfessorEntity professor = professorRepository.findById(payload.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con ID: " + payload.getProfessorId()));

      //  List<GradeEntity> grades = gradeRepository.findAllById(payload.getGradeIds());

        subject.setSubjectName(payload.getSubjectName().trim());
        subject.setProfessorEntity(professor);
       // subject.setGradeEntities(grades);

        subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + id));

        subjectRepository.delete(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubjectDto> findBySubjectName(String subjectName, Pageable pageable) {
        return subjectRepository.findBySubjectNameContainingIgnoreCase(subjectName, pageable)
                .map(factory::subjectDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<SubjectDto> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable)
                .map(factory::subjectDto);
    }

    @Override
    public Page<SubjectDto> findByProfessorEntity_Id(Long professorId, Pageable pageable) {
        return subjectRepository.findByProfessorEntity_Id(professorId, pageable)
                .map(factory::subjectDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDto findById(Long id) {
        return subjectRepository.findById(id)
                .map(factory::subjectDto)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + id));
    }
}
