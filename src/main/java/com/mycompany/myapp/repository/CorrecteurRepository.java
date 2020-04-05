package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Correcteur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Correcteur entity.
 */
@Repository
public interface CorrecteurRepository extends JpaRepository<Correcteur, Long> {

    @Query(value = "select distinct correcteur from Correcteur correcteur left join fetch correcteur.matieres",
        countQuery = "select count(distinct correcteur) from Correcteur correcteur")
    Page<Correcteur> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct correcteur from Correcteur correcteur left join fetch correcteur.matieres")
    List<Correcteur> findAllWithEagerRelationships();

    @Query("select correcteur from Correcteur correcteur left join fetch correcteur.matieres where correcteur.id =:id")
    Optional<Correcteur> findOneWithEagerRelationships(@Param("id") Long id);
}
