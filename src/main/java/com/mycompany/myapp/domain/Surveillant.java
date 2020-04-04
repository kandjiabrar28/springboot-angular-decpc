package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Surveillant.
 */
@Entity
@Table(name = "surveillant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Surveillant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fonction")
    private String fonction;

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

    @ManyToOne
    @JsonIgnoreProperties("surveillants")
    private PVSurveillance pvsurveillance;

    @ManyToMany(mappedBy = "surveillants")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Salle> salles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFonction() {
        return fonction;
    }

    public Surveillant fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getPrenom() {
        return prenom;
    }

    public Surveillant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Surveillant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProvenance() {
        return provenance;
    }

    public Surveillant provenance(String provenance) {
        this.provenance = provenance;
        return this;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getCni() {
        return cni;
    }

    public Surveillant cni(String cni) {
        this.cni = cni;
        return this;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getTelephone() {
        return telephone;
    }

    public Surveillant telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public Surveillant sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDatenais() {
        return datenais;
    }

    public Surveillant datenais(LocalDate datenais) {
        this.datenais = datenais;
        return this;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Surveillant dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public Surveillant dateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public PVSurveillance getPvsurveillance() {
        return pvsurveillance;
    }

    public Surveillant pvsurveillance(PVSurveillance pVSurveillance) {
        this.pvsurveillance = pVSurveillance;
        return this;
    }

    public void setPvsurveillance(PVSurveillance pVSurveillance) {
        this.pvsurveillance = pVSurveillance;
    }

    public Set<Salle> getSalles() {
        return salles;
    }

    public Surveillant salles(Set<Salle> salles) {
        this.salles = salles;
        return this;
    }

    public Surveillant addSalle(Salle salle) {
        this.salles.add(salle);
        salle.getSurveillants().add(this);
        return this;
    }

    public Surveillant removeSalle(Salle salle) {
        this.salles.remove(salle);
        salle.getSurveillants().remove(this);
        return this;
    }

    public void setSalles(Set<Salle> salles) {
        this.salles = salles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Surveillant)) {
            return false;
        }
        return id != null && id.equals(((Surveillant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Surveillant{" +
            "id=" + getId() +
            ", fonction='" + getFonction() + "'" +
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
