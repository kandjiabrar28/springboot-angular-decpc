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
 * A TAnonym.
 */
@Entity
@Table(name = "t_anonym")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TAnonym implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numanonym")
    private String numanonym;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @OneToMany(mappedBy = "tAnonym")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tour> tours = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tanonyms")
    private Candidat candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumanonym() {
        return numanonym;
    }

    public TAnonym numanonym(String numanonym) {
        this.numanonym = numanonym;
        return this;
    }

    public void setNumanonym(String numanonym) {
        this.numanonym = numanonym;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public TAnonym dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public TAnonym dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public TAnonym tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public TAnonym addTour(Tour tour) {
        this.tours.add(tour);
        tour.setTAnonym(this);
        return this;
    }

    public TAnonym removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setTAnonym(null);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public TAnonym candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TAnonym)) {
            return false;
        }
        return id != null && id.equals(((TAnonym) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TAnonym{" +
            "id=" + getId() +
            ", numanonym='" + getNumanonym() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
