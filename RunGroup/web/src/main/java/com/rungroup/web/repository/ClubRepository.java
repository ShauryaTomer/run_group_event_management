package com.rungroup.web.repository;

import com.rungroup.web.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByTitle(String url); //these are custom query methods, Java finds the property inside the actual Club model and then creates the actual piece of code for you
    @Query("SELECT c FROM Club c WHERE  UPPER(c.title) LIKE UPPER(CONCAT ('%', :query ,'%'))")
    List<Club> searchClubs(String query);
}
