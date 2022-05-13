package com.example.postofficebackend.letter.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
public class Letter implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    private String uniqueId;
    private String name;
    private String status;

    private int time;




}
