package ru.sergeich.diploma.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "bouquet_id")
    private List<Bouquet> bouquets;

    //private int count;

    private LocalDate date;

    public Order(User user, List<Bouquet> bouquet) {
        this.user = user;
        this.bouquets = bouquet;
        this.date = LocalDate.now();
    }
}
