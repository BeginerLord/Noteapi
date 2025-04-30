package com.appscol.schedule.services.implementation;

import com.appscol.schedule.persistence.entities.SchedulesEntity;
import com.appscol.schedule.persistence.repositories.ScheduleRepository;
import com.appscol.schedule.presentation.payload.SchedulePayload;
import com.appscol.schedule.services.interfaces.IScheduleService;
import com.appscol.section.persistence.entities.SectionsEntity;
import com.appscol.section.persistence.repositories.SectionRepository;
import com.appscol.subject.persistence.entities.SubjectEntity;
import com.appscol.subject.persistence.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {

    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional()
    public void createSchedule(SchedulePayload payload) {

        SectionsEntity section = sectionRepository.findById(payload.getSectionId())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        SubjectEntity subject = subjectRepository.findById(payload.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));


        SchedulesEntity schedules = modelMapper.map(payload, SchedulesEntity.class);
        scheduleRepository.save(schedules);

    }

}
