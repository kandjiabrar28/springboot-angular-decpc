package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Plage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlageRepository extends JpaRepository<Plage, Long> {
}
