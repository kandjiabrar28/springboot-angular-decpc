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
 * A Salle.
 */
@Entity
@Table(name = "salle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numsalle")
    private Integer numsalle;

    @Column(name = "nomsalle")
    private String nomsalle;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @OneToMany(mappedBy = "salle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tables> tables = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("salles")
    private PVSurveillance pvsurveillance;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "salle_surveillant",
               joinColumns = @JoinColumn(name = "salle_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "surveillant_id", referencedColumnName = "id"))
    private Set<Surveillant> surveillants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("salles")
    private Centre centre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumsalle() {
        return numsalle;
    }

    public Salle numsalle(Integer numsalle) {
        this.numsalle = numsalle;
        return this;
    }

    public void setNumsalle(Integer numsalle) {
        this.numsalle = numsalle;
    }

    public String getNomsalle() {
        return nomsalle;
    }

    public Salle nomsalle(String nomsalle) {
        this.nomsalle = nomsalle;
        return this;
    }

    public void setNomsalle(String nomsalle) {
        this.nomsalle = nomsalle;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Salle dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Salle dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Set<Tables> getTables() {
        return tables;
    }

    public Salle tables(Set<Tables> tables) {
        this.tables = tables;
        return this;
    }

    public Salle addTables(Tables tables) {
        this.tables.add(tables);
        tables.setSalle(this);
        return this;
    }

    public Salle removeTables(Tables tables) {
        this.tables.remove(tables);
        tables.setSalle(null);
        return this;
    }

    public void setTables(Set<Tables> tables) {
        this.tables = tables;
    }

    public PVSurveillance getPvsurveillance() {
        return pvsurveillance;
    }

    public Salle pvsurveillance(PVSurveillance pVSurveillance) {
        this.pvsurveillance = pVSurveillance;
        return this;
    }

    public void setPvsurveillance(PVSurveillance pVSurveillance) {
        this.pvsurveillance = pVSurveillance;
    }

    public Set<Surveillant> getSurveillants() {
        return surveillants;
    }

    public Salle surveillants(Set<Surveillant> surveillants) {
        this.surveillants = surveillants;
        return this;
    }

    public Salle addSurveillant(Surveillant surveillant) {
        this.surveillants.add(surveillant);
        surveillant.getSalles().add(this);
        return this;
    }

    public Salle removeSurveillant(Surveillant surveillant) {
        this.surveillants.remove(surveillant);
        surveillant.getSalles().remove(this);
        return this;
    }

    public void setSurveillants(Set<Surveillant> surveillants) {
        this.surveillants = surveillants;
    }

    public Centre getCentre() {
        return centre;
    }

    public Salle centre(Centre centre) {
        this.centre = centre;
        return this;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Salle)) {
            return false;
        }
        return id != null && id.equals(((Salle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Salle{" +
            "id=" + getId() +
            ", numsalle=" + getNumsalle() +
            ", nomsalle='" + getNomsalle() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
