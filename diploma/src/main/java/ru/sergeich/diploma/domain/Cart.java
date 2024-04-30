package ru.sergeich.diploma.domain;

import javax.persistence.*;

@Entity(name = "cart")
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userID;

    private int bouquetNumber;
    private int bouquetCount;

    public int getBouquetCount() {
        return bouquetCount;
    }

    public void setBouquetCount(int bouquetCount) {
        this.bouquetCount = bouquetCount;
    }

    public Cart(User userID, int bouquetNumber) {
        this.userID = userID;
        this.bouquetNumber = bouquetNumber;
    }

    public Cart() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserID() {
        return userID;
    }

    public String getPhotoAddress(){
        return "bouquet_" + String.valueOf(bouquetNumber) + ".jpg";
    }

    public String getPrice(boolean totalCost){
        switch (this.bouquetNumber){
            case 1:
                if (totalCost)
                    return String.valueOf(1500 * bouquetCount) + " руб.";
                else
                    return String.valueOf(1500) + " руб.";
            case 2:
                if (totalCost)
                    return String.valueOf(1800 * bouquetCount) + " руб.";
                else
                    return String.valueOf(1800) + " руб.";
            case 3:
                if (totalCost)
                    return String.valueOf(2300 * bouquetCount) + " руб.";
                else
                    return String.valueOf(2300) + " руб.";
            case 4:
                if (totalCost)
                    return String.valueOf(5000 * bouquetCount) + " руб.";
                else
                    return String.valueOf(5000) + " руб.";
            case 5:
                if (totalCost)
                    return String.valueOf(3100 * bouquetCount) + " руб.";
                else
                    return String.valueOf(3100) + " руб.";
            case 6:
                if (totalCost)
                    return String.valueOf(2800 * bouquetCount) + " руб.";
                else
                    return String.valueOf(2800) + " руб.";
            case 7:
                if (totalCost)
                    return String.valueOf(1900 * bouquetCount) + " руб.";
                else
                    return String.valueOf(1900) + " руб.";
            case 8:
                if (totalCost)
                    return String.valueOf(2600 * bouquetCount) + " руб.";
                else
                    return String.valueOf(2600) + " руб.";
            case 9:
                if (totalCost)
                    return String.valueOf(2400 * bouquetCount) + " руб.";
                else
                    return String.valueOf(2400) + " руб.";
            default:
                return "Неизвестно";
        }
    }

    public String getName(){
        switch (this.bouquetNumber){
            case 1:
                return "Букет Микс";
            case 2:
                return "Букет Комплимент";
            case 3:
                return "Букет Свадебный";
            case 4:
                return "Букет Джентельмен";
            case 5:
                return "Букет Вьюга";
            case 6:
                return "Букет Джунгли";
            case 7:
                return "Букет Восход";
            case 8:
                return "Букет Стихия";
            case 9:
                return "Букет Удача";
            default:
                return "Неизвестный букет";
        }
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

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public int getBouquetNumber() {
        return bouquetNumber;
    }

    public void setbouquetNumber(int bouquetNumber) {
        this.bouquetNumber = bouquetNumber;
    }
}