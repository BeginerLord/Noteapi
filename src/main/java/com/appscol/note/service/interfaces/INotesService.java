package com.appscol.note.service.interfaces;

import com.appscol.note.presentation.dto.NotesDto;
import com.appscol.note.presentation.payload.AddNotePayload;
import com.appscol.note.presentation.payload.NotesPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface INotesService {

    void saveNotes(NotesPayload notesPayload);
    void updateNotes(Long id, NotesPayload notesPayload);
    void deleteNotes(Long id);
    Page<NotesDto> findAll (Pageable pageable);
    void addNote(AddNotePayload payload, UUID professorUuid);
    Page<NotesDto> getNotesByProfessor(Pageable pageable, UUID professorUuid);
    Page<NotesDto> getNotesByStudent(Pageable pageable, UUID studentUuid);
    boolean isNoteOwnedByStudent(Long noteId, UUID studentUuid);
    NotesDto findById(Long id);
}
