package com.example.postofficebackend.letter.service.letterServiceImplementation;

import com.example.postofficebackend.letter.entity.Letter;
import com.example.postofficebackend.letter.enumeration.Status;
import com.example.postofficebackend.letter.repository.LetterRepositoryI;
import com.example.postofficebackend.letter.service.LetterServiceI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LetterServiceImplementation implements LetterServiceI {

    @Autowired
    private final LetterRepositoryI letterRepositoryI;

    @Override
    public List<Letter> getAll() {
        return letterRepositoryI.findAll();
    }

    @Override
    public List<Letter> getAllSorted() {
        return letterRepositoryI
                .findAll(Sort.by(Sort.Direction.ASC, "time"))
                .stream()
                .filter(c -> !c.getStatus().equals("INSTANT"))
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(fixedDelay = 20, timeUnit = TimeUnit.SECONDS)
    public void refreshQueue() {
        //System.out.println("aktualnie: "+date.getTime());

        List<Letter> letterList = getAll();

        for (Letter letter : letterList) {
            letter.setTime((letter.getTime() - 20));
            if (letter.getTime() == 0) {
                delete(letter.getId());
            } else {
                update(letter);
            }
        }
    }

    @Override
    public Letter add(String name, String content, String pin) {

        if (letterRepositoryI.checkIfNotEmpty().isEmpty()){
            refreshQueue();
        }

        if (pin.equals("8888")) {
            Letter newLetter = new Letter();
            newLetter.setName(name);
            newLetter.setContent(content);
            int lastVip = getLastVip();
            if (lastVip > 0) {

                newLetter.setTime(lastVip);
            } else {
                newLetter.setTime(getFirst() + 20);
            }

            addTimeToAll();
            newLetter.setStatus(getStatusEnumName("Vip").name());
            newLetter.setUniqueId(generateUniqueId());
            return this.letterRepositoryI.save(newLetter);
        } else if (pin.equals("0000")) {
            Letter newLetter = new Letter();
            newLetter.setName(name);
            newLetter.setContent(content);
            newLetter.setTime(60);
            newLetter.setStatus(getStatusEnumName("Instant").name());
            newLetter.setUniqueId(generateUniqueId());
            return letterRepositoryI.save(newLetter);
        } else {
            Letter newLetter = new Letter();
            newLetter.setName(name);
            newLetter.setContent(content);
            newLetter.setTime(getLast() + 20);
            newLetter.setStatus(getStatusEnumName("Normal").name());
            newLetter.setUniqueId(generateUniqueId());
            return letterRepositoryI.save(newLetter);
        }
    }

    @Override
    public List<Letter> getAllLettersBefore(String uniqueId) {
        Letter letter = getByUniqueId(uniqueId);
        List<Letter> letterList;
        if (letter.getStatus().equals("INSTANT")) {
            letterList = new ArrayList<>();
        } else {
            letterList = getAllSorted()
                    .stream()
                    .filter(c -> c.getTime() < letter.getTime())
                    .collect(Collectors.toList());
        }
        letterList.add(letter);
        return letterList;
    }

    public Letter getById(Long id) {
        Optional<Letter> letterOptional = this.letterRepositoryI.findById(id);
        return letterOptional.get();
    }

    public int getLast() {
        try {
            List<Letter> letterList = letterRepositoryI.findAll(Sort.by(Sort.Direction.DESC, "time"));
            return letterList.get(0).getTime();
        } catch (Exception e) {
            return 0;
        }


    }

    public int getFirst() {
        try {
            List<Letter> letterList = letterRepositoryI.findAll(Sort.by(Sort.Direction.ASC, "time"));
            return letterList.get(0).getTime();
        } catch (Exception e) {
            return 0;
        }

    }

    public Letter getByUniqueId(String uniqueId) {
        return letterRepositoryI.findByUniqueId(uniqueId);
    }


    public void addTimeToAll() {
        List<Letter> letterList = getAll();
        try {
            if (letterList.get(0).getStatus().equals("NORMAL")) {
                letterList.remove(0);
            }
        } catch (Exception ignored) {

        }

        for (Letter letter : letterList) {
            if (letter.getStatus().equals("INSTANT") || letter.getStatus().equals("VIP")) {
                continue;
            } else {
                letter.setTime(letter.getTime() + 20);
                update(letter);
            }
        }
    }

    public int getLastVip() {
        try {
            List<Letter> letterList = letterRepositoryI.findAll(Sort.by(Sort.Direction.DESC, "time"))
                    .stream().filter(c -> c.getStatus().equals("VIP")).collect(Collectors.toList());
            return letterList.get(0).getTime() + 20;
        } catch (Exception e) {
            return -1;
        }
    }

    public Letter update(Letter letter) {
        Optional<Letter> currentLetterOptional = this.letterRepositoryI.findById(letter.getId());

        Letter currentLetter = currentLetterOptional.get();

        currentLetter.setContent(letter.getContent());
        currentLetter.setName(letter.getName());
        currentLetter.setTime(letter.getTime());
        return letterRepositoryI.save(currentLetter);
    }

    public void delete(Long id) {
        letterRepositoryI.deleteById(id);
    }

    private Status getStatusEnumName(String status) {
        return Status.valueOf(status.toUpperCase());
    }

    private String generateUniqueId() {
        return RandomStringUtils.randomNumeric(10);
    }

}
