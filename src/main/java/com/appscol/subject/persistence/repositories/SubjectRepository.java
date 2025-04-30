package com.appscol.subject.persistence.repositories;

import com.appscol.subject.persistence.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

}
