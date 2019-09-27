package com.evolve.server.service;

import com.evolve.model.Trip;
import com.evolve.server.repository.TripParticipantRepository;
import com.evolve.server.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    TripParticipantRepository tripParticipantRepository;

    public Collection<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
}
