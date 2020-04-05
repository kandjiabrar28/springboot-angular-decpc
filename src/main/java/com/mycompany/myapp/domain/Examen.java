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

    @Column(name = "date_examen")
    private LocalDate dateExamen;

    @OneToMany(mappedBy = "examen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(mappedBy = "examen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tour> tours = new HashSet<>();

    @ManyToMany(mappedBy = "examen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
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

    public LocalDate getDateExamen() {
        return dateExamen;
    }

    public Examen dateExamen(LocalDate dateExamen) {
        this.dateExamen = dateExamen;
        return this;
    }

    public void setDateExamen(LocalDate dateExamen) {
        this.dateExamen = dateExamen;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Examen sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Examen addSession(Session session) {
        this.sessions.add(session);
        session.setExamen(this);
        return this;
    }

    public Examen removeSession(Session session) {
        this.sessions.remove(session);
        session.setExamen(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public Examen tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public Examen addTour(Tour tour) {
        this.tours.add(tour);
        tour.setExamen(this);
        return this;
    }

    public Examen removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setExamen(null);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
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
            ", dateExamen='" + getDateExamen() + "'" +
            "}";
    }
}
