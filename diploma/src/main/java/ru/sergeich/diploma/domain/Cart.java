package ru.sergeich.diploma.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "cart")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "bouquet_id")
    private List<Bouquet> bouquets;

    public Cart(User user) {
        this.user = user;
    }

    public Cart() {

    }

}