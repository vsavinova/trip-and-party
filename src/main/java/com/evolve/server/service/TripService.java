package com.evolve.server.service;

import com.evolve.model.AcceptStatus;
import com.evolve.model.Trip;
import com.evolve.model.TripParticipant;
import com.evolve.server.repository.TripParticipantRepository;
import com.evolve.server.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

import static com.evolve.server.common.Constants.ADMIN_ROLE;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    TripParticipantRepository tripParticipantRepository;

    public Collection<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip createTrip(Trip trip) {
        trip.setParticipants(Collections.singletonList(
                createTripParticipant(trip.getOrgId(), trip, ADMIN_ROLE, AcceptStatus.ACCEPTED)));
        return tripRepository.save(trip);
    }

    public Trip apply(Integer tripId, Integer vkId) {
        Trip trip = tripRepository.findById(tripId).get();
//        TripParticipant participant = new TripParticipant();
//        participant.setUserId(vkId);
//        participant.setTrip(trip);
//        participant.setAccept_status(AcceptStatus.UNDEFINED);
        TripParticipant participant = createTripParticipant(vkId, trip, "", AcceptStatus.UNDEFINED);
        tripParticipantRepository.save(participant);
        return tripRepository.findById(tripId).get();
    }

    private TripParticipant createTripParticipant(int userId, Trip trip, String role, AcceptStatus acceptStatus) {
        TripParticipant tripParticipant = new TripParticipant();
        tripParticipant.setUserId(userId);
        tripParticipant.setTrip(trip);
        tripParticipant.setRole(role);
        tripParticipant.setAccept_status(acceptStatus);
        return tripParticipant;
    }

    public Trip respondOnUserRequest(Integer tripParticipantId, Integer orgId, AcceptStatus response) {
        TripParticipant tripParticipant = tripParticipantRepository.findById(tripParticipantId).get();
        Trip trip = tripParticipant.getTrip();
        System.out.println(trip.getId());
//        Trip trip = tripRepository.findById(tripId).get();
        if (trip.getOrgId() != orgId)
            throw new IllegalArgumentException("Wrong orgID");

        tripParticipant.setAccept_status(response);
        tripParticipantRepository.save(tripParticipant);
        return trip;
    }
}
