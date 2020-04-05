package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
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

    @OneToMany(mappedBy = "matiere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "matiere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tour> tours = new HashSet<>();

    @ManyToMany(mappedBy = "matieres")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
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

    public Set<Note> getNotes() {
        return notes;
    }

    public Matiere notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Matiere addNote(Note note) {
        this.notes.add(note);
        note.setMatiere(this);
        return this;
    }

    public Matiere removeNote(Note note) {
        this.notes.remove(note);
        note.setMatiere(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<Tour> getTours() {
        return tours;
    }

    public Matiere tours(Set<Tour> tours) {
        this.tours = tours;
        return this;
    }

    public Matiere addTour(Tour tour) {
        this.tours.add(tour);
        tour.setMatiere(this);
        return this;
    }

    public Matiere removeTour(Tour tour) {
        this.tours.remove(tour);
        tour.setMatiere(null);
        return this;
    }

    public void setTours(Set<Tour> tours) {
        this.tours = tours;
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
            "}";
    }
}
