package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Plage.
 */
@Entity
@Table(name = "plage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plage")
    private String plage;

    @Column(name = "typecopie")
    private String typecopie;

    @Column(name = "nombrecopie")
    private Integer nombrecopie;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @ManyToOne
    @JsonIgnoreProperties("plages")
    private Correcteur correcteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlage() {
        return plage;
    }

    public Plage plage(String plage) {
        this.plage = plage;
        return this;
    }

    public void setPlage(String plage) {
        this.plage = plage;
    }

    public String getTypecopie() {
        return typecopie;
    }

    public Plage typecopie(String typecopie) {
        this.typecopie = typecopie;
        return this;
    }

    public void setTypecopie(String typecopie) {
        this.typecopie = typecopie;
    }

    public Integer getNombrecopie() {
        return nombrecopie;
    }

    public Plage nombrecopie(Integer nombrecopie) {
        this.nombrecopie = nombrecopie;
        return this;
    }

    public void setNombrecopie(Integer nombrecopie) {
        this.nombrecopie = nombrecopie;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Plage dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Plage dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Correcteur getCorrecteur() {
        return correcteur;
    }

    public Plage correcteur(Correcteur correcteur) {
        this.correcteur = correcteur;
        return this;
    }

    public void setCorrecteur(Correcteur correcteur) {
        this.correcteur = correcteur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plage)) {
            return false;
        }
        return id != null && id.equals(((Plage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plage{" +
            "id=" + getId() +
            ", plage='" + getPlage() + "'" +
            ", typecopie='" + getTypecopie() + "'" +
            ", nombrecopie=" + getNombrecopie() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
