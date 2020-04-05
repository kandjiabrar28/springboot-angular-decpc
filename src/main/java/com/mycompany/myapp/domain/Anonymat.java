package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Anonymat.
 */
@Entity
@Table(name = "anonymat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anonymat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numanoymat")
    private Integer numanoymat;

    @ManyToOne
    @JsonIgnoreProperties("anonymats")
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties("anonymats")
    private Tour tour;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumanoymat() {
        return numanoymat;
    }

    public Anonymat numanoymat(Integer numanoymat) {
        this.numanoymat = numanoymat;
        return this;
    }

    public void setNumanoymat(Integer numanoymat) {
        this.numanoymat = numanoymat;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Anonymat candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Tour getTour() {
        return tour;
    }

    public Anonymat tour(Tour tour) {
        this.tour = tour;
        return this;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anonymat)) {
            return false;
        }
        return id != null && id.equals(((Anonymat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Anonymat{" +
            "id=" + getId() +
            ", numanoymat=" + getNumanoymat() +
            "}";
    }
}
