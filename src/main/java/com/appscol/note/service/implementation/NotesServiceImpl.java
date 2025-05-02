package com.appscol.note.service.implementation;

import com.appscol.note.factory.NotesFactory;
import com.appscol.note.persistence.entities.NotesEntity;
import com.appscol.note.persistence.repositories.NotesRepository;
import com.appscol.note.presentation.dto.NotesDto;
import com.appscol.note.presentation.payload.NotesPayload;
import com.appscol.note.service.interfaces.INotesService;
import com.appscol.student.persistence.repositories.StudentRepository;
import com.appscol.subject.persistence.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements INotesService {

    private final ModelMapper modelMapper;
    private final NotesRepository notesRepository;
    private final NotesFactory notesFactory;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional()
    public void saveNotes(NotesPayload notesPayload) {
        NotesEntity notes = modelMapper.map(notesPayload, NotesEntity.class);
        notesRepository.save(notes);
    }

    @Override
    @Transactional()
    public void updateNotes(Long id, NotesPayload notesPayload) {
    NotesEntity notes = notesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nota no encontrado con ID: " + id));
    modelMapper.map(notesPayload, notes);
    notesRepository.save(notes);
    }

    @Override
    @Transactional()
    public void deleteNotes(Long id) {
    NotesEntity notes = notesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Nota no encontrado con ID: " + id));
    notesRepository.delete(notes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotesDto> findAll(Pageable pageable) {
        return notesRepository.findAll(pageable).map(notesFactory::notesDto);
    }
}
