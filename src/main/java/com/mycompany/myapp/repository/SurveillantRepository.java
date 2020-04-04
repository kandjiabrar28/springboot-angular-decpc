package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Surveillant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Surveillant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveillantRepository extends JpaRepository<Surveillant, Long> {
}
