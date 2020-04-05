package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Candidat.
 */
@Entity
@Table(name = "candidat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "provenance")
    private String provenance;

    @Column(name = "cni")
    private String cni;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "datenais")
    private LocalDate datenais;

    @Column(name = "niveau")
    private String niveau;

    @OneToOne
    @JoinColumn(unique = true)
    private Table table;

    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anonymat> anonymats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public Candidat prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Candidat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProvenance() {
        return provenance;
    }

    public Candidat provenance(String provenance) {
        this.provenance = provenance;
        return this;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getCni() {
        return cni;
    }

    public Candidat cni(String cni) {
        this.cni = cni;
        return this;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getTelephone() {
        return telephone;
    }

    public Candidat telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public Candidat sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDatenais() {
        return datenais;
    }

    public Candidat datenais(LocalDate datenais) {
        this.datenais = datenais;
        return this;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public String getNiveau() {
        return niveau;
    }

    public Candidat niveau(String niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Table getTable() {
        return table;
    }

    public Candidat table(Table table) {
        this.table = table;
        return this;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Candidat notes(Set<Note> notes) {
        this.notes = notes;
        return this;
    }

    public Candidat addNote(Note note) {
        this.notes.add(note);
        note.setCandidat(this);
        return this;
    }

    public Candidat removeNote(Note note) {
        this.notes.remove(note);
        note.setCandidat(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<Anonymat> getAnonymats() {
        return anonymats;
    }

    public Candidat anonymats(Set<Anonymat> anonymats) {
        this.anonymats = anonymats;
        return this;
    }

    public Candidat addAnonymat(Anonymat anonymat) {
        this.anonymats.add(anonymat);
        anonymat.setCandidat(this);
        return this;
    }

    public Candidat removeAnonymat(Anonymat anonymat) {
        this.anonymats.remove(anonymat);
        anonymat.setCandidat(null);
        return this;
    }

    public void setAnonymats(Set<Anonymat> anonymats) {
        this.anonymats = anonymats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidat)) {
            return false;
        }
        return id != null && id.equals(((Candidat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", provenance='" + getProvenance() + "'" +
            ", cni='" + getCni() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", datenais='" + getDatenais() + "'" +
            ", niveau='" + getNiveau() + "'" +
            "}";
    }
}
