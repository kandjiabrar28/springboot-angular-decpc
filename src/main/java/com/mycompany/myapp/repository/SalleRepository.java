package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Salle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Salle entity.
 */
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

    @Query(value = "select distinct salle from Salle salle left join fetch salle.surveillants",
        countQuery = "select count(distinct salle) from Salle salle")
    Page<Salle> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct salle from Salle salle left join fetch salle.surveillants")
    List<Salle> findAllWithEagerRelationships();

    @Query("select salle from Salle salle left join fetch salle.surveillants where salle.id =:id")
    Optional<Salle> findOneWithEagerRelationships(@Param("id") Long id);
}
