package com.codegoons.diesli.repository;

import com.codegoons.diesli.domain.Motto;

import com.codegoons.diesli.domain.MottoDefinition;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Motto entity.
 */
public interface MottoRepository extends JpaRepository<Motto,Long> {

    @Query("select motto from Motto motto where motto.user.login = ?#{principal.username}")
    List<Motto> findByUserIsCurrentUser();

    @Query("SELECT motto FROM Motto motto WHERE motto.nombre LIKE LOWER (CONCAT(:searchTerm,'%'))")
    List<Motto> searchTerm(@Param("searchTerm") String searchTerm);


}
