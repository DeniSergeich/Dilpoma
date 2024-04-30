package ru.sergeich.diploma.domain;

import lombok.Data;

import javax.persistence.*;
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
    private int price;

    private String description;
    @ManyToMany(mappedBy = "bouquetList")
    private Set<Cart> cartList;
}

