package com.example.postofficebackend.letter.controller;



import com.example.postofficebackend.letter.entity.Letter;
import com.example.postofficebackend.letter.service.LetterServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@EnableScheduling
@RestController
@RequestMapping(value = {"/letter"})
public class LetterController {

    @Autowired
    private LetterServiceI letterServiceI;


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

    @GetMapping("/uID/{uniqueId}")
    public ResponseEntity<List<Letter>> getAllLettersBefore(@PathVariable String uniqueId){
        return ResponseEntity.ok(letterServiceI
                .getAllLettersBefore(uniqueId));

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Letter>> getAllLettersBeforeName(@PathVariable String name){
        return ResponseEntity.ok(letterServiceI
                .getAllLettersBeforeName(name));

    }

    @GetMapping("/instant")
    public ResponseEntity<List<Letter>> getAllInstant(){
        return ResponseEntity.ok(letterServiceI
                .getAllInstants());

    }

    @PostMapping
    public ResponseEntity<Letter> add(@RequestParam("name") String name,
                                      @RequestParam("pin") String pin){
        return ResponseEntity.ok(letterServiceI
                .add(name, pin));
    }


}
