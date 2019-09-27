package com.evolve.server.repository;

import com.evolve.model.TripParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TripParticipantRepository  extends CrudRepository<TripParticipant, Integer> {
}
