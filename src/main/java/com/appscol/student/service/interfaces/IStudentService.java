package com.appscol.student.service.interfaces;

import com.appscol.student.presentation.dto.StudentDto;
import com.appscol.student.presentation.payload.StudentPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IStudentService {

    void save(StudentPayload payload);
    void update(StudentPayload payload, UUID uuid);
    void  deleteByUuid(UUID uuid);
    Page<StudentDto>findAll (Pageable pageable);
    StudentDto findByUuid(UUID uuid);
}
