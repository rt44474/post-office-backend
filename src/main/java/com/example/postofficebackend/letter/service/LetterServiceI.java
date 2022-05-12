package com.example.postofficebackend.letter.service;

import com.example.postofficebackend.letter.entity.Letter;

import java.util.List;

public interface LetterServiceI {
    List<Letter> getAll();
    List<Letter> getAllSorted();
    Letter getById(Long id);
    Letter getByUniqueId(Long uniqueId);
    Letter add(String name, String content, String pin);
    Letter update(Letter letter);
    int getLast();
    int getFirst();
    List<Letter> getAllLettersBefore(Long uniqueId);
    void refreshQueue();
    void delete(Long id);
}
