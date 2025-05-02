package com.appscol.note.service.interfaces;

import com.appscol.note.presentation.dto.NotesDto;
import com.appscol.note.presentation.payload.NotesPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INotesService {

    void saveNotes(NotesPayload notesPayload);
    void updateNotes(Long id, NotesPayload notesPayload);
    void deleteNotes(Long id);
    Page<NotesDto> findAll (Pageable pageable);
}
