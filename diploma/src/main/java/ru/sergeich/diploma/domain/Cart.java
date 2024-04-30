package ru.sergeich.diploma.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cart")
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;

    private int bouquetNumber;
    private int bouquetCount;

    public Cart(User user, int bouquetNumber) {
    }

    public String getPhotoAddress() {
        return "bouquet" + String.valueOf(bouquetNumber) + ".jpg";
    }

    public String getPrice(boolean withDiscount) {
        switch (this.bouquetNumber){
            case 1:
                if (withDiscount) return String.valueOf(4390 * bouquetCount) + " руб.";
                else return String.valueOf(4390) + " руб.";
            case 2:
                if (withDiscount) return String.valueOf(2980 * bouquetCount) + " руб.";
                else return String.valueOf(2980) + " руб.";
            case 3:
                if (withDiscount) return String.valueOf(5710 * bouquetCount) + " руб.";
                else return String.valueOf(5710) + " руб.";
            case 4:
                if (withDiscount) return String.valueOf(2990 * bouquetCount) + " руб.";
                else return String.valueOf(2990) + " руб.";
            case 5:
                if (withDiscount) return String.valueOf(3090 * bouquetCount) + " руб.";
                else return String.valueOf(3090) + " руб.";
            case 6:
                if (withDiscount) return String.valueOf(2160 * bouquetCount) + " руб.";
                else return String.valueOf(2160) + " руб.";
            case 7:
                if (withDiscount) return String.valueOf(4040 * bouquetCount) + " руб.";
                else return String.valueOf(4040) + " руб.";
            case 8:
                if (withDiscount) return String.valueOf(4300 * bouquetCount) + " руб.";
                else return String.valueOf(4300) + " руб.";
            case 9:
                if (withDiscount) return String.valueOf(3840 * bouquetCount) + " руб.";
                else return String.valueOf(3840) + " руб.";
            default:
                return "Неизвестно";
        }
    }

    public String getBouquetName() {
        switch (this.bouquetNumber){
            case 1:
                return "NINA";
            case 2:
                return "Нежность";
            case 3:
                return "My princess";
            case 4:
                return "Благодарность";
            case 5:
                return "PINK WINE";
            case 6:
                return "Радость";
            case 7:
                return "Притяжение";
            case 8:
                return "Танго";
            case 9:
                return "Дамский каприз";
            default:
                return "Неизвестный букет";
        }
    }

    public int getBouquetNumber() {
        return bouquetNumber;
    }

    public int getBouquetCount() {
        return bouquetCount;
    }


}
