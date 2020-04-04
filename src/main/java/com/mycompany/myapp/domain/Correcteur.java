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
 * A Correcteur.
 */
@Entity
@Table(name = "correcteur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Correcteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private String grade;

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

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @ManyToMany(mappedBy = "correcteurs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Matiere> matieres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public Correcteur grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPrenom() {
        return prenom;
    }

    public Correcteur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Correcteur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProvenance() {
        return provenance;
    }

    public Correcteur provenance(String provenance) {
        this.provenance = provenance;
        return this;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getCni() {
        return cni;
    }

    public Correcteur cni(String cni) {
        this.cni = cni;
        return this;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getTelephone() {
        return telephone;
    }

    public Correcteur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public Correcteur sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDatenais() {
        return datenais;
    }

    public Correcteur datenais(LocalDate datenais) {
        this.datenais = datenais;
        return this;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Correcteur dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Correcteur dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Set<Matiere> getMatieres() {
        return matieres;
    }

    public Correcteur matieres(Set<Matiere> matieres) {
        this.matieres = matieres;
        return this;
    }

    public Correcteur addMatiere(Matiere matiere) {
        this.matieres.add(matiere);
        matiere.getCorrecteurs().add(this);
        return this;
    }

    public Correcteur removeMatiere(Matiere matiere) {
        this.matieres.remove(matiere);
        matiere.getCorrecteurs().remove(this);
        return this;
    }

    public void setMatieres(Set<Matiere> matieres) {
        this.matieres = matieres;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Correcteur)) {
            return false;
        }
        return id != null && id.equals(((Correcteur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Correcteur{" +
            "id=" + getId() +
            ", grade='" + getGrade() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", provenance='" + getProvenance() + "'" +
            ", cni='" + getCni() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", datenais='" + getDatenais() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
