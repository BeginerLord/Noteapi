package com.appscol.note.service.implementation;

import com.appscol.helpers.exception.exceptions.ResourceNotFoundException;
import com.appscol.note.factory.NotesFactory;
import com.appscol.note.persistence.entities.NotesEntity;
import com.appscol.note.persistence.repositories.NotesRepository;
import com.appscol.note.presentation.dto.NotesDto;
import com.appscol.note.presentation.payload.AddNotePayload;
import com.appscol.note.presentation.payload.NotesPayload;
import com.appscol.note.service.interfaces.INotesService;
import com.appscol.professor.persistence.entities.ProfessorEntity;
import com.appscol.professor.persistence.repositories.ProfessorRepository;
import com.appscol.student.persistence.entities.StudentEntity;
import com.appscol.student.persistence.repositories.StudentRepository;
import com.appscol.subject.persistence.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements INotesService {

    private final ModelMapper modelMapper;
    private final NotesRepository notesRepository;
    private final NotesFactory notesFactory;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;

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

    @Override
    @Transactional
    public void addNote(AddNotePayload payload, UUID professorUuid) {
        ProfessorEntity professor = professorRepository.findByUuid(professorUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado"));

        // Obtener el estudiante
        StudentEntity student = studentRepository.findByUuid(payload.getStudentUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado"));

        // Crear la nueva entidad de nota
        NotesEntity newNote = NotesEntity.builder()
                //.professorEntity(professor)
                .studentEntity(student)
                .valor(payload.getValor())  // Valor de la nota
                .periodo(payload.getPeriodo()) // Período de la nota
                .build();

        // Guardar la nueva nota en el repositorio
        notesRepository.save(newNote);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotesDto> getNotesByProfessor(Pageable pageable, UUID professorUuid) {
        return notesRepository.findByProfessorUuid(pageable, professorUuid).map(notesFactory::notesDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotesDto> getNotesByStudent(Pageable pageable, UUID studentUuid) {
        return notesRepository.findByStudentUuid(pageable, studentUuid).map(notesFactory::notesDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isNoteOwnedByStudent(Long noteId, UUID studentUuid) {
        return notesRepository.existsByIdAndStudentEntityUuid(noteId, studentUuid);
    }

    @Override
    @Transactional(readOnly = true)
    public NotesDto findById(Long id) {
        NotesEntity note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota no encontrada con ID: " + id));
        return notesFactory.notesDto(note);
    }
}
