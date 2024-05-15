package ru.sergeich.diploma.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_bouquet",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "bouquet_id"))
    private List<Bouquet> bouquets;

    private LocalDate date;

    public Order(User user, List<Bouquet> bouquet) {
        this.user = user;
        this.bouquets = bouquet;
        this.date = LocalDate.now();
    }
    public Double getTotalCost() {
        return this.bouquets.stream()
                .mapToDouble(Bouquet::getPrice)
                .sum();
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
