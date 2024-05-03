package ru.sergeich.diploma.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.sergeich.diploma.repositoryes.BouquetRepository;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "cart")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
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