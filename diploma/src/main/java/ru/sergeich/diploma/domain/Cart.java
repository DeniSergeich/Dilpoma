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
    @OneToOne
    @JoinColumn(name = "bouquet_id")
    private Bouquet bouquet;

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
        double totalPrice = withDiscount ? bouquet.getPrice() * bouquetCount : bouquet.getPrice();
        return String.valueOf(totalPrice) + " руб.";
    }

    public String getBouquetName() {
        return bouquet.getName();
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