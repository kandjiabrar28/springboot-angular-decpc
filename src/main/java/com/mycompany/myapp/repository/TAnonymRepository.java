package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TAnonym;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TAnonym entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TAnonymRepository extends JpaRepository<TAnonym, Long> {
}
