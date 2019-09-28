package com.evolve.server.repository;

import com.evolve.model.Trip;
import com.evolve.model.Visibility;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Integer> {
    Collection<Trip> findAll();

    @Query("select t from Trip as t " +
            "inner join t.guide as g " +
            "   where t.id in (" +
            "           select tp.trip from t.participants as tp" +
            "                   where (tp.userId in :friendsVkIds)" +
            "                       and (tp.accept_status = 1)) ")
    List<Trip> findFriendsTrips(@Param("friendsVkIds") Collection<Integer> friendsVkIds);
}
