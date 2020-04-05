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
 * A Niveau.
 */
@Entity
@Table(name = "niveau")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Niveau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libniveau")
    private String libniveau;

    @ManyToMany(mappedBy = "niveaus")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Specialite> specialites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibniveau() {
        return libniveau;
    }

    public Niveau libniveau(String libniveau) {
        this.libniveau = libniveau;
        return this;
    }

    public void setLibniveau(String libniveau) {
        this.libniveau = libniveau;
    }

    public Set<Specialite> getSpecialites() {
        return specialites;
    }

    public Niveau specialites(Set<Specialite> specialites) {
        this.specialites = specialites;
        return this;
    }

    public Niveau addSpecialite(Specialite specialite) {
        this.specialites.add(specialite);
        specialite.getNiveaus().add(this);
        return this;
    }

    public Niveau removeSpecialite(Specialite specialite) {
        this.specialites.remove(specialite);
        specialite.getNiveaus().remove(this);
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
        if (!(o instanceof Niveau)) {
            return false;
        }
        return id != null && id.equals(((Niveau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Niveau{" +
            "id=" + getId() +
            ", libniveau='" + getLibniveau() + "'" +
            "}";
    }
}
