package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomexamen")
    private String nomexamen;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Tour tour;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "examen_jury",
               joinColumns = @JoinColumn(name = "examen_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "jury_id", referencedColumnName = "id"))
    private Set<Jury> juries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomexamen() {
        return nomexamen;
    }

    public Examen nomexamen(String nomexamen) {
        this.nomexamen = nomexamen;
        return this;
    }

    public void setNomexamen(String nomexamen) {
        this.nomexamen = nomexamen;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Examen dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Examen dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Tour getTour() {
        return tour;
    }

    public Examen tour(Tour tour) {
        this.tour = tour;
        return this;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Set<Jury> getJuries() {
        return juries;
    }

    public Examen juries(Set<Jury> juries) {
        this.juries = juries;
        return this;
    }

    public Examen addJury(Jury jury) {
        this.juries.add(jury);
        jury.getExamen().add(this);
        return this;
    }

    public Examen removeJury(Jury jury) {
        this.juries.remove(jury);
        jury.getExamen().remove(this);
        return this;
    }

    public void setJuries(Set<Jury> juries) {
        this.juries = juries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        return id != null && id.equals(((Examen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", nomexamen='" + getNomexamen() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
