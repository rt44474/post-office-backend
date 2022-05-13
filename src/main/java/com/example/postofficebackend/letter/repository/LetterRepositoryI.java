package com.example.postofficebackend.letter.repository;

import com.example.postofficebackend.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LetterRepositoryI extends JpaRepository<Letter, Long> {
    Letter findByUniqueId(String uniqueId);
    Letter findByName(String name);

    @Query("select 1 from Letter")
    List<Integer> checkIfNotEmpty();
}
