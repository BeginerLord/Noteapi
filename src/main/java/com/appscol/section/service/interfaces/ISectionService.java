package com.appscol.section.service.interfaces;


import com.appscol.section.presentation.dto.SectionDto;
import com.appscol.section.presentation.payload.SectionPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ISectionService {
    void save(SectionPayload payload);
    void update(SectionPayload payload, UUID uuid);
    void  deleteById(Long id);
    Page<SectionDto> findByGrade(String grade, Pageable pageable);
    Page<SectionDto>findAll (Pageable pageable);
    SectionDto findById(Long id);
}
