package com.mentorhub.repository;

import com.mentorhub.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findByMentorId(Integer mentorId);
    List<MessageEntity> findByMenteeId(Integer menteeId);
}
