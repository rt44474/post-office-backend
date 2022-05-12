package com.example.postofficebackend.letter.repository;

import com.example.postofficebackend.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepositoryI extends JpaRepository<Letter, Long> {
    Letter findByUniqueId(Long uniqueId);
}
