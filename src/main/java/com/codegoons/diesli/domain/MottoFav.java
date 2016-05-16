package com.codegoons.diesli.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MottoFav.
 */
@Entity
@Table(name = "motto_fav")
public class MottoFav implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Motto motto;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Motto getMotto() {
        return motto;
    }

    public void setMotto(Motto motto) {
        this.motto = motto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MottoFav mottoFav = (MottoFav) o;
        if(mottoFav.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mottoFav.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MottoFav{" +
            "id=" + id +
            '}';
    }
}
