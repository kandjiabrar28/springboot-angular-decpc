package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Examen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Examen entity.
 */
@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    @Query(value = "select distinct examen from Examen examen left join fetch examen.juries",
        countQuery = "select count(distinct examen) from Examen examen")
    Page<Examen> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct examen from Examen examen left join fetch examen.juries")
    List<Examen> findAllWithEagerRelationships();

    @Query("select examen from Examen examen left join fetch examen.juries where examen.id =:id")
    Optional<Examen> findOneWithEagerRelationships(@Param("id") Long id);
}
