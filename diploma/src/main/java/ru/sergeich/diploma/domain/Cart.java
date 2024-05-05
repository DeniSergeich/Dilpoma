package ru.sergeich.diploma.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_bouquet",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "bouquet_id")
    )
    private List<Bouquet> bouquets;

    public Cart(User user) {
        this.user = user;
        this.bouquets = new ArrayList<>();
    }

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Cart() {

    }

    public int countBouquetsById(Long bouquetId) {
        return (int) this.bouquets.stream()
                .filter(b -> b.getId().equals(bouquetId))
                .count();
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", bouquets=" + bouquets +
                '}';
    }

}