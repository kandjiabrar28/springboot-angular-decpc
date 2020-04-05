package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlageCopie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlageCopie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlageCopieRepository extends JpaRepository<PlageCopie, Long> {
}
