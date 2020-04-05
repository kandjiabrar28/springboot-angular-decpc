package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jury;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Jury entity.
 */
@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {

    @Query(value = "select distinct jury from Jury jury left join fetch jury.examen",
        countQuery = "select count(distinct jury) from Jury jury")
    Page<Jury> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct jury from Jury jury left join fetch jury.examen")
    List<Jury> findAllWithEagerRelationships();

    @Query("select jury from Jury jury left join fetch jury.examen where jury.id =:id")
    Optional<Jury> findOneWithEagerRelationships(@Param("id") Long id);
}
