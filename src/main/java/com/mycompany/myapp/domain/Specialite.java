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
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libspec")
    private String libspec;

    @OneToMany(mappedBy = "specialite")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "specialite_niveau",
               joinColumns = @JoinColumn(name = "specialite_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "niveau_id", referencedColumnName = "id"))
    private Set<Niveau> niveaus = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("specialites")
    private Filiere filiere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibspec() {
        return libspec;
    }

    public Specialite libspec(String libspec) {
        this.libspec = libspec;
        return this;
    }

    public void setLibspec(String libspec) {
        this.libspec = libspec;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Specialite sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Specialite addSession(Session session) {
        this.sessions.add(session);
        session.setSpecialite(this);
        return this;
    }

    public Specialite removeSession(Session session) {
        this.sessions.remove(session);
        session.setSpecialite(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Set<Niveau> getNiveaus() {
        return niveaus;
    }

    public Specialite niveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
        return this;
    }

    public Specialite addNiveau(Niveau niveau) {
        this.niveaus.add(niveau);
        niveau.getSpecialites().add(this);
        return this;
    }

    public Specialite removeNiveau(Niveau niveau) {
        this.niveaus.remove(niveau);
        niveau.getSpecialites().remove(this);
        return this;
    }

    public void setNiveaus(Set<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public Specialite filiere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialite)) {
            return false;
        }
        return id != null && id.equals(((Specialite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", libspec='" + getLibspec() + "'" +
            "}";
    }
}
