package com.example.postofficebackend.letter.controller;


import com.example.postofficebackend.letter.entity.Letter;
import com.example.postofficebackend.letter.service.LetterServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableScheduling
@RestController
@RequestMapping(value = {"/letter"})
public class LetterController {

    @Autowired
    private LetterServiceI letterServiceI;

    @Scheduled(cron="*/20 * * * * *")
    @GetMapping("/refresh")
    public void refresh(){
        letterServiceI.refreshQueue();
    }

    @GetMapping
    public ResponseEntity<List<Letter>> getAll(){
        return ResponseEntity.ok(letterServiceI
                .getAll());
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Letter>> getAllSorted(){
        return ResponseEntity.ok(letterServiceI
                .getAllSorted());
    }

    @PostMapping
    public ResponseEntity<Letter> add(@RequestParam("name") String name,
                                      @RequestParam("content") String content,
                                      @RequestParam("pin") String pin){
        return ResponseEntity.ok(letterServiceI
                .add(name,content, pin));
    }


}
