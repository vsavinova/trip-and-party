package com.evolve.server.service;

import com.evolve.model.AcceptStatus;
import com.evolve.model.Trip;
import com.evolve.model.TripParticipant;
import com.evolve.model.Visibility;
import com.evolve.server.repository.TripParticipantRepository;
import com.evolve.server.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public Collection<Trip> getTrips(Integer userId, String city, Visibility visibility,
                                     LocalDate startDate, LocalDate finishDate) {
        Collection<Integer> friendsVkIds = getFriendsVkIds(userId);
        List<Trip> friendsTrips = tripRepository.findFriendsTrips(friendsVkIds); //, city, startDate, finishDate);
        System.out.println("count: " + friendsTrips.size());

        return fillFriendsParticipantsInTrips(friendsTrips, friendsVkIds);
    }

    private Collection<Trip> fillFriendsParticipantsInTrips(Collection<Trip> trips, Collection<Integer> friendsVkIds) {
        for (Trip trip : trips) {
            List<TripParticipant> collect = trip.getParticipants()
                    .stream()
                    .filter(p -> friendsVkIds.contains(p.getUserId()))
                    .collect(Collectors.toList());
            trip.setFriendParticipants(
                    collect);
            System.out.println("" + trip.getId() + " " + collect.size());
        }
        return trips;
    }

    private Collection<Integer> getFriendsVkIds(Integer userId) {
        return Arrays.asList(654321, 123456, 1111, 666, 188181, 3333, 0); // TODO: 27.09.2019 get from VK API
    }

    private TripParticipant createTripParticipant(int userId, Trip trip, String role, AcceptStatus acceptStatus) {
        TripParticipant tripParticipant = new TripParticipant();
        tripParticipant.setUserId(userId);
        tripParticipant.setTrip(trip);
        tripParticipant.setRole(role);
        tripParticipant.setAccept_status(acceptStatus);
        return tripParticipant;
    }
}
