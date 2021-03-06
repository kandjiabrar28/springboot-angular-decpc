package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Tables;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TablesRepository extends JpaRepository<Tables, Long> {
}
