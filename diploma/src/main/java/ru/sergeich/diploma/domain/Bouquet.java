package ru.sergeich.diploma.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bouquets")
public class Bouquet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @NonNull
    private String name;
    private int amount;
    private double price;
    private String description;
    private String image;

    @ManyToMany(mappedBy = "bouquets", fetch = FetchType.EAGER)
    private List<Cart> carts;


    public Bouquet(@NonNull String name, int amount, double price, String description, String image) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Bouquet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        Bouquet other = (Bouquet) obj;
        return id == other.id;
    }

}
