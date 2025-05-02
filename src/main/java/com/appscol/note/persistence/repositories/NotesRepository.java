package com.appscol.note.persistence.repositories;

import com.appscol.note.persistence.entities.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<NotesEntity, Long> {

}
