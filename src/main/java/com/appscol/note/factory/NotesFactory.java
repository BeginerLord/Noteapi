package com.appscol.note.factory;

import com.appscol.note.persistence.entities.NotesEntity;
import com.appscol.note.presentation.dto.NotesDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotesFactory {
    private final ModelMapper modelMapper;

    public NotesDto notesDto(NotesEntity notesEntity){
        return NotesDto.builder()
                //.id(String.valueOf(gradeEntity.getId()))
                .valor(notesEntity.getValor())
                .periodo(notesEntity.getPeriodo())
                .build();
    }
}
