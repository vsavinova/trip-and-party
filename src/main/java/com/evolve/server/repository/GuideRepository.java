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
}
