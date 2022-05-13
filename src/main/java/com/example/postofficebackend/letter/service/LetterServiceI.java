package com.example.postofficebackend.letter.service;

import com.example.postofficebackend.letter.entity.Letter;

import java.util.List;

public interface LetterServiceI {
    List<Letter> getAll();
    List<Letter> getAllSorted();
    List<Letter> getAllLettersBefore(String uniqueId);
    List<Letter> getAllLettersBeforeName(String name);
    Letter add(String name, String pin);
    List<Letter> getAllInstants();





}
