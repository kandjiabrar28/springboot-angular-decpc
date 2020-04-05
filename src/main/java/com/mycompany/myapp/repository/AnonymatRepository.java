package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Anonymat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Anonymat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnonymatRepository extends JpaRepository<Anonymat, Long> {
}
