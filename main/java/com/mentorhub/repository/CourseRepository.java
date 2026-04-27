package com.mentorhub.repository;

import com.mentorhub.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    List<CourseEntity> findByMentorId(Integer mentorId);
}
