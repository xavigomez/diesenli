package com.codegoons.diesli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Motto.
 */
@Entity
@Table(name = "motto")
public class Motto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "informacion_etimologica")
    private String informacionEtimologica;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "motto")
    @JsonIgnore
    private Set<MottoDefinition> mottoDefinitions = new HashSet<>();

    @OneToMany(mappedBy = "motto")
    @JsonIgnore
    private Set<Definition> definitions = new HashSet<>();

    @OneToMany(mappedBy = "motto")
    @JsonIgnore
    private Set<MottoFav> mottoFavs = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformacionEtimologica() {
        return informacionEtimologica;
    }

    public void setInformacionEtimologica(String informacionEtimologica) {
        this.informacionEtimologica = informacionEtimologica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<MottoDefinition> getMottoDefinitions() {
        return mottoDefinitions;
    }

    public void setMottoDefinitions(Set<MottoDefinition> mottoDefinitions) {
        this.mottoDefinitions = mottoDefinitions;
    }

    public Set<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Set<Definition> definitions) {
        this.definitions = definitions;
    }

    public Set<MottoFav> getMottoFavs() {
        return mottoFavs;
    }

    public void setMottoFavs(Set<MottoFav> mottoFavs) {
        this.mottoFavs = mottoFavs;
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
        Motto motto = (Motto) o;
        if(motto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, motto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Motto{" +
            "id=" + id +
            ", informacionEtimologica='" + informacionEtimologica + "'" +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
