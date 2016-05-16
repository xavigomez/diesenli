package com.codegoons.diesli.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MottoDefinition.
 */
@Entity
@Table(name = "motto_definition")
public class MottoDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "materia")
    private String materia;

    @Column(name = "registro")
    private String registro;

    @Column(name = "region")
    private String region;

    @Column(name = "ejemplo")
    private String ejemplo;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "popularity")
    private Long popularity;

    @ManyToOne
    private MottoDefinition sinonimo;

    @ManyToOne
    private MottoDefinition antonimo;

    @ManyToOne
    private MottoDefinition lemaSemejanza;

    @ManyToOne
    private MottoDefinition lemaOposicion;

    @ManyToOne
    private Motto motto;

    @ManyToOne
    private Definition definition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public MottoDefinition getSinonimo() {
        return sinonimo;
    }

    public void setSinonimo(MottoDefinition mottoDefinition) {
        this.sinonimo = mottoDefinition;
    }

    public MottoDefinition getAntonimo() {
        return antonimo;
    }

    public void setAntonimo(MottoDefinition mottoDefinition) {
        this.antonimo = mottoDefinition;
    }

    public MottoDefinition getLemaSemejanza() {
        return lemaSemejanza;
    }

    public void setLemaSemejanza(MottoDefinition mottoDefinition) {
        this.lemaSemejanza = mottoDefinition;
    }

    public MottoDefinition getLemaOposicion() {
        return lemaOposicion;
    }

    public void setLemaOposicion(MottoDefinition mottoDefinition) {
        this.lemaOposicion = mottoDefinition;
    }

    public Motto getMotto() {
        return motto;
    }

    public void setMotto(Motto motto) {
        this.motto = motto;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MottoDefinition mottoDefinition = (MottoDefinition) o;
        if(mottoDefinition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mottoDefinition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MottoDefinition{" +
            "id=" + id +
            ", materia='" + materia + "'" +
            ", registro='" + registro + "'" +
            ", region='" + region + "'" +
            ", ejemplo='" + ejemplo + "'" +
            ", categoria='" + categoria + "'" +
            ", popularity='" + popularity + "'" +
            '}';
    }
}
