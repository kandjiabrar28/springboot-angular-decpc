package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Table;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Table entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
}
