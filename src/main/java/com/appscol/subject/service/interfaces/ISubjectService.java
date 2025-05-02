package com.appscol.subject.service.interfaces;

import com.appscol.section.presentation.dto.SectionDto;
import com.appscol.subject.presentation.dto.SubjectDto;
import com.appscol.subject.presentation.payload.SubjectPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISubjectService {
    void save(SubjectPayload payload);
    void update(SubjectPayload payload,Long id);
    void  deleteById(Long id);
    Page<SubjectDto> findBySubjectName(String subjectName, Pageable pageable);
    Page<SubjectDto>findAll (Pageable pageable);
    Page<SubjectDto> findByProfessorEntity_Id(Long professorId, Pageable pageable);
    SubjectDto findById(Long id);
}
