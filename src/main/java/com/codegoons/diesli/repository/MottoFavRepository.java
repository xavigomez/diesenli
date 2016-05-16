package com.codegoons.diesli.repository;

import com.codegoons.diesli.domain.MottoFav;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MottoFav entity.
 */
public interface MottoFavRepository extends JpaRepository<MottoFav,Long> {

    @Query("select mottoFav from MottoFav mottoFav where mottoFav.user.login = ?#{principal.username}")
    List<MottoFav> findByUserIsCurrentUser();

}
