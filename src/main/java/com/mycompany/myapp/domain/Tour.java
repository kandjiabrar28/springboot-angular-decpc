package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tour.
 */
@Entity
@Table(name = "tour")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numtour")
    private Integer numtour;

    @OneToMany(mappedBy = "tour")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anonymat> anonymats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tours")
    private Matiere matiere;

    @ManyToOne
    @JsonIgnoreProperties("tours")
    private Examen examen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumtour() {
        return numtour;
    }

    public Tour numtour(Integer numtour) {
        this.numtour = numtour;
        return this;
    }

    public void setNumtour(Integer numtour) {
        this.numtour = numtour;
    }

    public Set<Anonymat> getAnonymats() {
        return anonymats;
    }

    public Tour anonymats(Set<Anonymat> anonymats) {
        this.anonymats = anonymats;
        return this;
    }

    public Tour addAnonymat(Anonymat anonymat) {
        this.anonymats.add(anonymat);
        anonymat.setTour(this);
        return this;
    }

    public Tour removeAnonymat(Anonymat anonymat) {
        this.anonymats.remove(anonymat);
        anonymat.setTour(null);
        return this;
    }

    public void setAnonymats(Set<Anonymat> anonymats) {
        this.anonymats = anonymats;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Tour matiere(Matiere matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Examen getExamen() {
        return examen;
    }

    public Tour examen(Examen examen) {
        this.examen = examen;
        return this;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tour)) {
            return false;
        }
        return id != null && id.equals(((Tour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tour{" +
            "id=" + getId() +
            ", numtour=" + getNumtour() +
            "}";
    }
}
