package com.codegoons.diesli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Definition.
 */
@Entity
@Table(name = "definition")
public class Definition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "definition")
    private String definition;

    @OneToMany(mappedBy = "definition")
    @JsonIgnore
    private Set<MottoDefinition> mottoDefinitions = new HashSet<>();

    @ManyToOne
    private Motto motto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Set<MottoDefinition> getMottoDefinitions() {
        return mottoDefinitions;
    }

    public void setMottoDefinitions(Set<MottoDefinition> mottoDefinitions) {
        this.mottoDefinitions = mottoDefinitions;
    }

    public Motto getMotto() {
        return motto;
    }

    public void setMotto(Motto motto) {
        this.motto = motto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Definition definition = (Definition) o;
        if(definition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, definition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Definition{" +
            "id=" + id +
            ", definition='" + definition + "'" +
            '}';
    }
}
