package com.appscol.section.service.implementation;

import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.section.factory.SectionFactory;
import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.section.persistence.repositories.SectionRepository;
import com.appscol.section.presentation.dto.SectionDto;
import com.appscol.section.presentation.payload.SectionPayload;
import com.appscol.section.service.interfaces.ISectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements ISectionService {
    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;
    private final SectionFactory sectionFactory;

    @Override
    @Transactional()
    public void save(SectionPayload payload) {
        SectionsEntity section = SectionsEntity.builder()
                .sectionName(payload.getSectionName().trim())
                //.gradeEntity(graE)
                .build();

        sectionRepository.save(section);
    }

    @Override
    @Transactional()
    public void update(SectionPayload payload,Long id) {
        SectionsEntity sections= sectionRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("No existe la seccion con ID:"+ id)
        );
        modelMapper.map(payload, sections);
        sectionRepository.save(sections);

    }

    @Override
    @Transactional()
    public void deleteById(Long id) {
        SectionsEntity sections= sectionRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("No existe la seccion con ID:"+ id)
        );
        sectionRepository.delete(sections);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SectionDto> findByGrade(String grade, Pageable pageable) {
        return sectionRepository.findByGradeEntity_Grade(grade, pageable).map(sectionFactory::sectionDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SectionDto> findAll(Pageable pageable) {
        return sectionRepository.findAll(pageable)
                .map(section -> modelMapper.map(section, SectionDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public SectionDto findById(Long id) {
        return sectionRepository.findById(id)
                .map(section -> modelMapper.map(section, SectionDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe la seccion con ID: " + id));
    }
}
