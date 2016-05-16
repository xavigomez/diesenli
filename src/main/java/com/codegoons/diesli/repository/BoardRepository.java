package com.codegoons.diesli.repository;

import com.codegoons.diesli.domain.Board;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Board entity.
 */
public interface BoardRepository extends JpaRepository<Board,Long> {

}
