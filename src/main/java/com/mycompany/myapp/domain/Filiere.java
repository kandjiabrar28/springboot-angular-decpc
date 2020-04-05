package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Filiere.
 */
@Entity
@Table(name = "filiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libfiliere")
    private String libfiliere;

    @OneToMany(mappedBy = "filiere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Specialite> specialites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibfiliere() {
        return libfiliere;
    }

    public Filiere libfiliere(String libfiliere) {
        this.libfiliere = libfiliere;
        return this;
    }

    public void setLibfiliere(String libfiliere) {
        this.libfiliere = libfiliere;
    }

    public Set<Specialite> getSpecialites() {
        return specialites;
    }

    public Filiere specialites(Set<Specialite> specialites) {
        this.specialites = specialites;
        return this;
    }

    public Filiere addSpecialite(Specialite specialite) {
        this.specialites.add(specialite);
        specialite.setFiliere(this);
        return this;
    }

    public Filiere removeSpecialite(Specialite specialite) {
        this.specialites.remove(specialite);
        specialite.setFiliere(null);
        return this;
    }

    public void setSpecialites(Set<Specialite> specialites) {
        this.specialites = specialites;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filiere)) {
            return false;
        }
        return id != null && id.equals(((Filiere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Filiere{" +
            "id=" + getId() +
            ", libfiliere='" + getLibfiliere() + "'" +
            "}";
    }
}
