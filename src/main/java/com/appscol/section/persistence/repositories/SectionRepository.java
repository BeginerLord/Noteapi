package com.appscol.section.persistence.repositories;

import com.appscol.section.persistence.entities.SectionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionsEntity, Long> {

}
