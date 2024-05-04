package ru.sergeich.diploma.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "user")
@Table(name = "users")
@Data
@AllArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Size(min=1, message = "Не может быть пустым!")
    private String username;

    @Size(min=5, message = "Пароль не может быть меньше 5 символов!")
    private String password;

    @Transient
    private String passwordConfirm;
    private String email;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User() {
    }

    public User(String username, String password, String passwordConfirm, String email) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.cart = new Cart(this);
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

    public int getBCount(Long bouquetId) {
        return (int) orders.get(1).getCount();
    }


}
