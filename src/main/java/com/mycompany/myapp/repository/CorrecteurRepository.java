package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Correcteur;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Correcteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrecteurRepository extends JpaRepository<Correcteur, Long> {
}
