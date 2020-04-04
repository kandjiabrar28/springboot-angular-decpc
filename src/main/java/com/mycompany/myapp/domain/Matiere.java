package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libmatiere")
    private String libmatiere;

    @Column(name = "noteelimin")
    private Double noteelimin;

    @Column(name = "coefficient")
    private Double coefficient;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "matiere_correcteur",
               joinColumns = @JoinColumn(name = "matiere_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "correcteur_id", referencedColumnName = "id"))
    private Set<Correcteur> correcteurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibmatiere() {
        return libmatiere;
    }

    public Matiere libmatiere(String libmatiere) {
        this.libmatiere = libmatiere;
        return this;
    }

    public void setLibmatiere(String libmatiere) {
        this.libmatiere = libmatiere;
    }

    public Double getNoteelimin() {
        return noteelimin;
    }

    public Matiere noteelimin(Double noteelimin) {
        this.noteelimin = noteelimin;
        return this;
    }

    public void setNoteelimin(Double noteelimin) {
        this.noteelimin = noteelimin;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public Matiere coefficient(Double coefficient) {
        this.coefficient = coefficient;
        return this;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Matiere dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Matiere dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Set<Correcteur> getCorrecteurs() {
        return correcteurs;
    }

    public Matiere correcteurs(Set<Correcteur> correcteurs) {
        this.correcteurs = correcteurs;
        return this;
    }

    public Matiere addCorrecteur(Correcteur correcteur) {
        this.correcteurs.add(correcteur);
        correcteur.getMatieres().add(this);
        return this;
    }

    public Matiere removeCorrecteur(Correcteur correcteur) {
        this.correcteurs.remove(correcteur);
        correcteur.getMatieres().remove(this);
        return this;
    }

    public void setCorrecteurs(Set<Correcteur> correcteurs) {
        this.correcteurs = correcteurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matiere)) {
            return false;
        }
        return id != null && id.equals(((Matiere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", libmatiere='" + getLibmatiere() + "'" +
            ", noteelimin=" + getNoteelimin() +
            ", coefficient=" + getCoefficient() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
