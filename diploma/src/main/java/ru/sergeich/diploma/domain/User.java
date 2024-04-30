package ru.sergeich.diploma.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
@Table(name = "users")
@Data
@AllArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;

    @Size(min=2, message = "Не меньше 5 знаков")
    private String username;
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;
    @Transient
    private String passwordConfirm;
    private String email;

    @OneToMany(mappedBy = "userID", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Cart> list = new HashSet<Cart>();

    public User() {
    }

    public User(String username, String password, String passwordConfirm, String email) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    public int getBCount(int bouquetNumber){
        for (Cart item : list){
            if (item.getBouquetNumber() == bouquetNumber){
                return item.getBouquetCount();
            }
        }
        return 0;
    }

    public Cart getB(int bouquerNumber){
        for (Cart item: list){
            if (item.getBouquetNumber() == bouquerNumber)
                return item;
        }
        return null;
    }

    public String getAllTotalCost(){
        int total = 0;
        for (Cart item: this.list){
            String temp = item.getPrice(true).replaceAll("\\D+","");
            total += Integer.parseInt(temp);
        }
        return String.valueOf(total) + " руб.";
    }

}
