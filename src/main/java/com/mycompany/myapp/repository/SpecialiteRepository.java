package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Specialite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Specialite entity.
 */
@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {

    @Query(value = "select distinct specialite from Specialite specialite left join fetch specialite.niveaus",
        countQuery = "select count(distinct specialite) from Specialite specialite")
    Page<Specialite> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct specialite from Specialite specialite left join fetch specialite.niveaus")
    List<Specialite> findAllWithEagerRelationships();

    @Query("select specialite from Specialite specialite left join fetch specialite.niveaus where specialite.id =:id")
    Optional<Specialite> findOneWithEagerRelationships(@Param("id") Long id);
}
