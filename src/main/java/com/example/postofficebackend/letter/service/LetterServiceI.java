package com.example.postofficebackend.letter.service;

import com.example.postofficebackend.letter.entity.Letter;

import java.util.List;

public interface LetterServiceI {
    List<Letter> getAll();
    List<Letter> getAllSorted();
    List<Letter> getAllLettersBefore(String uniqueId);
    Letter add(String name, String content, String pin);
    void refreshQueue();





}
