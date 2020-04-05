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

    @OneToMany(mappedBy = "salle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Table> tables = new HashSet<>();

    @OneToMany(mappedBy = "salle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PVSurveillance> pvsurveillances = new HashSet<>();

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

    public Set<Table> getTables() {
        return tables;
    }

    public Salle tables(Set<Table> tables) {
        this.tables = tables;
        return this;
    }

    public Salle addTable(Table table) {
        this.tables.add(table);
        table.setSalle(this);
        return this;
    }

    public Salle removeTable(Table table) {
        this.tables.remove(table);
        table.setSalle(null);
        return this;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    public Set<PVSurveillance> getPvsurveillances() {
        return pvsurveillances;
    }

    public Salle pvsurveillances(Set<PVSurveillance> pVSurveillances) {
        this.pvsurveillances = pVSurveillances;
        return this;
    }

    public Salle addPvsurveillance(PVSurveillance pVSurveillance) {
        this.pvsurveillances.add(pVSurveillance);
        pVSurveillance.setSalle(this);
        return this;
    }

    public Salle removePvsurveillance(PVSurveillance pVSurveillance) {
        this.pvsurveillances.remove(pVSurveillance);
        pVSurveillance.setSalle(null);
        return this;
    }

    public void setPvsurveillances(Set<PVSurveillance> pVSurveillances) {
        this.pvsurveillances = pVSurveillances;
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
            "}";
    }
}
