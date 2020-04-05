package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A PVSurveillance.
 */
@Entity
@Table(name = "pv_surveillance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PVSurveillance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "epreuve")
    private String epreuve;

    @Column(name = "heure_deb")
    private ZonedDateTime heureDeb;

    @Column(name = "heure_fin")
    private ZonedDateTime heureFin;

    @Column(name = "datesurv")
    private LocalDate datesurv;

    @ManyToOne
    @JsonIgnoreProperties("pvsurveillances")
    private Salle salle;

    @ManyToOne
    @JsonIgnoreProperties("pvsurveillances")
    private Surveillant surveillant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEpreuve() {
        return epreuve;
    }

    public PVSurveillance epreuve(String epreuve) {
        this.epreuve = epreuve;
        return this;
    }

    public void setEpreuve(String epreuve) {
        this.epreuve = epreuve;
    }

    public ZonedDateTime getHeureDeb() {
        return heureDeb;
    }

    public PVSurveillance heureDeb(ZonedDateTime heureDeb) {
        this.heureDeb = heureDeb;
        return this;
    }

    public void setHeureDeb(ZonedDateTime heureDeb) {
        this.heureDeb = heureDeb;
    }

    public ZonedDateTime getHeureFin() {
        return heureFin;
    }

    public PVSurveillance heureFin(ZonedDateTime heureFin) {
        this.heureFin = heureFin;
        return this;
    }

    public void setHeureFin(ZonedDateTime heureFin) {
        this.heureFin = heureFin;
    }

    public LocalDate getDatesurv() {
        return datesurv;
    }

    public PVSurveillance datesurv(LocalDate datesurv) {
        this.datesurv = datesurv;
        return this;
    }

    public void setDatesurv(LocalDate datesurv) {
        this.datesurv = datesurv;
    }

    public Salle getSalle() {
        return salle;
    }

    public PVSurveillance salle(Salle salle) {
        this.salle = salle;
        return this;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Surveillant getSurveillant() {
        return surveillant;
    }

    public PVSurveillance surveillant(Surveillant surveillant) {
        this.surveillant = surveillant;
        return this;
    }

    public void setSurveillant(Surveillant surveillant) {
        this.surveillant = surveillant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PVSurveillance)) {
            return false;
        }
        return id != null && id.equals(((PVSurveillance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PVSurveillance{" +
            "id=" + getId() +
            ", epreuve='" + getEpreuve() + "'" +
            ", heureDeb='" + getHeureDeb() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", datesurv='" + getDatesurv() + "'" +
            "}";
    }
}
