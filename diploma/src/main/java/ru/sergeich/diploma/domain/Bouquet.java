package ru.sergeich.diploma.domain;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "bouquet")
@Table(name = "bouquets")
public class Bouquet {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @NonNull
    private String name;
    private int amount;
    private double price;
    private String description;

    @OneToOne(mappedBy = "bouquet")
    private Cart cart;
    @OneToMany(mappedBy = "bouquet")
    private List<Order> orders;
}
