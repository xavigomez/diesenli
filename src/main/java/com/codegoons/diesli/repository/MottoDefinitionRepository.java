package com.codegoons.diesli.repository;

import com.codegoons.diesli.domain.Motto;
import com.codegoons.diesli.domain.MottoDefinition;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MottoDefinition entity.
 */
public interface MottoDefinitionRepository extends JpaRepository<MottoDefinition,Long> {

    @Query("SELECT m FROM MottoDefinition m WHERE m.motto.id=:selectedMottoId")
    List<MottoDefinition> selectedMottoId(@Param("selectedMottoId") Long selectedMottoId);
}

