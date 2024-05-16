package ru.sergeich.diploma.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "carts")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public int countBouquetsById(Long bouquetId) {
        return (int) this.bouquets.stream()
                .filter(b -> b.getId() == bouquetId)
                .count();
    }
    public int getBCount() {
        return (int) this.bouquets.size();
    }
    public double getTotalPrice() {
        if(this.bouquets.isEmpty()) return 0;
        return this.bouquets.stream()
                .mapToDouble(Bouquet::getPrice)
                .sum();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", bouquets=" + bouquets +
                '}';
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}