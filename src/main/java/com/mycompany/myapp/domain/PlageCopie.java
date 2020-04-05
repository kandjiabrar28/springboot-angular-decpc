package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PlageCopie.
 */
@Entity
@Table(name = "plage_copie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlageCopie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plage")
    private String plage;

    @Column(name = "typecopie")
    private String typecopie;

    @Column(name = "nombrecopie")
    private Integer nombrecopie;

    @ManyToOne
    @JsonIgnoreProperties("plagecopies")
    private Correcteur correcteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlage() {
        return plage;
    }

    public PlageCopie plage(String plage) {
        this.plage = plage;
        return this;
    }

    public void setPlage(String plage) {
        this.plage = plage;
    }

    public String getTypecopie() {
        return typecopie;
    }

    public PlageCopie typecopie(String typecopie) {
        this.typecopie = typecopie;
        return this;
    }

    public void setTypecopie(String typecopie) {
        this.typecopie = typecopie;
    }

    public Integer getNombrecopie() {
        return nombrecopie;
    }

    public PlageCopie nombrecopie(Integer nombrecopie) {
        this.nombrecopie = nombrecopie;
        return this;
    }

    public void setNombrecopie(Integer nombrecopie) {
        this.nombrecopie = nombrecopie;
    }

    public Correcteur getCorrecteur() {
        return correcteur;
    }

    public PlageCopie correcteur(Correcteur correcteur) {
        this.correcteur = correcteur;
        return this;
    }

    public void setCorrecteur(Correcteur correcteur) {
        this.correcteur = correcteur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlageCopie)) {
            return false;
        }
        return id != null && id.equals(((PlageCopie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlageCopie{" +
            "id=" + getId() +
            ", plage='" + getPlage() + "'" +
            ", typecopie='" + getTypecopie() + "'" +
            ", nombrecopie=" + getNombrecopie() +
            "}";
    }
}
