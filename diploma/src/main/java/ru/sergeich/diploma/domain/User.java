package ru.sergeich.diploma.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Size(min=1, message = "Имя пользователя не может быть пустым")
    private String username;

    @Size(min=5, message = "Пароль не может содержать менее 5 символов")
    private String password;

    @Transient
    private String passwordConfirm;
    private String email;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Cart> list = new HashSet<Cart>();




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
