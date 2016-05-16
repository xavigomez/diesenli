package com.codegoons.diesli.repository;

import com.codegoons.diesli.domain.CardReply;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CardReply entity.
 */
public interface CardReplyRepository extends JpaRepository<CardReply,Long> {

}
