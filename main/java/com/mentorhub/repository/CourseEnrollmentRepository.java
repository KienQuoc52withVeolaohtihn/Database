package com.mentorhub.repository;

import com.mentorhub.entity.CourseEnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollmentEntity, Integer> {
    List<CourseEnrollmentEntity> findByMenteeId(Integer menteeId);
}
