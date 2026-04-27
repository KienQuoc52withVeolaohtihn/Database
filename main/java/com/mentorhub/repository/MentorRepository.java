package com.mentorhub.repository;

import com.mentorhub.entity.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<MentorEntity, Integer> {
}
