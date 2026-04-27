package com.mentorhub.repository;

import com.mentorhub.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    List<SessionEntity> findByMentorId(Integer mentorId);
}
