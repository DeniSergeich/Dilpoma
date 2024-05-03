package ru.sergeich.diploma.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Data
@Entity(name = "bouquet")
@Table(name = "bouquets")
public class Bouquet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NonNull
    private String name;
    private int amount;
    private double price;
    private String description;
    private String image;
    public Bouquet(@NonNull String name, int amount, double price, String description, String image) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.image = image;
    }
}
