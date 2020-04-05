package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Jury.
 */
@Entity
@Table(name = "jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jury implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numjury")
    private Integer numjury;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "jury_examen",
               joinColumns = @JoinColumn(name = "jury_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "examen_id", referencedColumnName = "id"))
    private Set<Examen> examen = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumjury() {
        return numjury;
    }

    public Jury numjury(Integer numjury) {
        this.numjury = numjury;
        return this;
    }

    public void setNumjury(Integer numjury) {
        this.numjury = numjury;
    }

    public Set<Examen> getExamen() {
        return examen;
    }

    public Jury examen(Set<Examen> examen) {
        this.examen = examen;
        return this;
    }

    public Jury addExamen(Examen examen) {
        this.examen.add(examen);
        examen.getJuries().add(this);
        return this;
    }

    public Jury removeExamen(Examen examen) {
        this.examen.remove(examen);
        examen.getJuries().remove(this);
        return this;
    }

    public void setExamen(Set<Examen> examen) {
        this.examen = examen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jury)) {
            return false;
        }
        return id != null && id.equals(((Jury) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Jury{" +
            "id=" + getId() +
            ", numjury=" + getNumjury() +
            "}";
    }
}
