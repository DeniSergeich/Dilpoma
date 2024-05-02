package ru.sergeich.diploma.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "bouquet")
@Table(name = "bouquets")
public class Bouquet {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;
    private String name;
    private int count;
    private double price;
    private String description;

    @OneToOne(mappedBy = "bouquet")
    private Cart cart;
}
