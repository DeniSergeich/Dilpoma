package ru.sergeich.diploma.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "cart")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userID;

    @OneToOne
    @JoinColumn(name = "bouquet_id")
    private Bouquet bouquet;

    private int bouquetCount;

    public Cart(User userID, int bouquetNumber) {
        this.userID = userID;
    }

    public Cart() {

    }

}