package ru.sergeich.diploma.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity(name = "cart")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userID;
    @ManyToMany
    private Set<Bouquet> bouquetList; // <>

    private int bouquetNumber;
    private int bouquetCount;

    public Cart(User userID, int bouquetNumber) {
        this.userID = userID;
        this.bouquetNumber = bouquetNumber;
    }

    public Cart() {

    }

    public String getPhotoAddress(){
        return "bouquet_" + String.valueOf(bouquetNumber) + ".jpg";
    }

    public String getPrice(boolean withDiscount) {
        double totalPrice = withDiscount ? bouquetList.iterator().next().getPrice() * bouquetCount : bouquetList.iterator().next().getPrice();
        return String.valueOf(totalPrice) + " руб.";
    }

    public String getBouquetName() {
        return bouquetList.iterator().next().getName();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userID=" + userID +
                ", bouquetNumber=" + bouquetNumber +
                ", bouquetCount=" + bouquetCount +
                '}';
    }

}