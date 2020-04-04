package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
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
    private String numjury;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @ManyToMany(mappedBy = "juries")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Examen> examen = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumjury() {
        return numjury;
    }

    public Jury numjury(String numjury) {
        this.numjury = numjury;
        return this;
    }

    public void setNumjury(String numjury) {
        this.numjury = numjury;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Jury dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Jury dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
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
            ", numjury='" + getNumjury() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
