package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jury;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Jury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {
}
