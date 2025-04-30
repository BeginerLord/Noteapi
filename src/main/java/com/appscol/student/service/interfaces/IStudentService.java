package com.appscol.student.service.interfaces;

import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.student.presentation.dto.StudentDto;
import com.appscol.student.presentation.payload.StudentPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentService {

    void save(StudentPayload payload);
    void update(StudentPayload payload, Long id);
    void deleteById(Long id);
    StudentDto findById(Long id);
    Page<StudentDto> findAll(Pageable pageable);

}
