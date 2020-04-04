package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PVSurveillance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PVSurveillance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PVSurveillanceRepository extends JpaRepository<PVSurveillance, Long> {
}
