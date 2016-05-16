package com.codegoons.diesli.repository;

import com.codegoons.diesli.domain.Definition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Definition entity.
 */
public interface DefinitionRepository extends JpaRepository<Definition,Long> {

}
