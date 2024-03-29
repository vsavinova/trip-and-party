package com.evolve.server.repository;

import com.evolve.model.Guide;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GuideRepository extends CrudRepository<Guide, Integer> {
    Collection<Guide> findAll();

    @Query("select g from Guide as g " +
            "   where ((:city is null) or (g.city = :city)) ")
    Collection<Guide> getAll(@Param("city") String city);
}
