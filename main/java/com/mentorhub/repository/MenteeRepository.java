package com.mentorhub.repository;

import com.mentorhub.entity.MenteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<MenteeEntity, Integer> {
}
