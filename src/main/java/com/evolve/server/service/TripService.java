package com.evolve.server.service;

import com.evolve.model.*;
import com.evolve.server.repository.TripParticipantRepository;
import com.evolve.server.repository.TripRepository;
import com.evolve.server.vk.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.evolve.server.common.Constants.ADMIN_ROLE;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    TripParticipantRepository tripParticipantRepository;
    @Autowired
    Getter getter;

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

    public Collection<Trip> getTrips(Integer userId, Visibility visibility, LocalDate startDate,
                                     LocalDate finishDate, String city, List<String> hashtags,
                                     String budget) {
        Collection<Trip> friendsTrips;
        Collection<Integer> friendsVkIds = getFriendsVkIds(userId);
        if (visibility == Visibility.ONE_HAND_FRIEND) {
            friendsTrips = tripRepository.findFriendsTrips(friendsVkIds); //, city, startDate, finishDate);
//            friendsTrips = filterTrips(friendsTrips, startDate, finishDate, city, hashtags, budget); // TODO: 29.09.2019 filter should be below
        } else {
            System.out.println("FIND ALL");
            friendsTrips = tripRepository.findAll();
            System.out.println("SIZE: " + friendsTrips.size());
        }

        return fillFriendsParticipantsInTrips(
//                friendsTrips,
                filterTrips(friendsTrips, startDate, finishDate, city, hashtags, budget),
                friendsVkIds);
    }

    private Collection<Trip> filterTrips(Collection<Trip> friendsTrips, LocalDate startDate, LocalDate finishDate,
                                         String city, List<String> hashtags, String budget) {
        return friendsTrips.stream()
                .filter(t -> t.getStartDate().isAfter(startDate)
                        && t.getFinishDate().isBefore(finishDate)
                        && (city.isEmpty()
                        || t.getGuide().getCity().equals(city))
                        && GuideService.compareBudget(t.getGuide().getBudget(), budget)
                        && compareHashTags(t.getGuide().getHashtags()
                        .stream().map(HashTag::getName)
                        .collect(Collectors.toList()), hashtags))
                .collect(Collectors.toList());
    }

    private boolean compareHashTags(List<String> gHashtags, List<String> hashtags) {
        return (hashtags.isEmpty()
                || !(new ArrayList<>(hashtags)
                .retainAll(gHashtags)));
    }

    private Collection<Trip> fillFriendsParticipantsInTrips(Collection<Trip> trips, Collection<Integer> friendsVkIds) {
        for (Trip trip : trips) {
            List<TripParticipant> collect = trip.getParticipants()
                    .stream()
                    .filter(p -> friendsVkIds.contains(p.getUserId()))
                    .collect(Collectors.toList());
            trip.setFriendParticipants(
                    collect);
        }
        return trips;
    }

    public Collection<Integer> getFriendsVkIds(Integer userId) {
        Map<String, Object> response = (Map<String, Object>) new Getter().getFriends(userId).get("response");
        if (response == null)
            return new ArrayList<>();
        List<Double> friends = (List<Double>) response.get("items");
//        System.out.println("friends: " + friends);
        return friends.stream()
                .map(Double::intValue)
                .collect(Collectors.toList());
    }

    private TripParticipant createTripParticipant(int userId, Trip trip, String role, AcceptStatus acceptStatus) {
        TripParticipant tripParticipant = new TripParticipant();
        tripParticipant.setUserId(userId);
        tripParticipant.setTrip(trip);
        tripParticipant.setRole(role);
        tripParticipant.setAccept_status(acceptStatus);
        return tripParticipant;
    }

    public Trip getTrip(Integer trip_id) {
        return tripRepository.findById(trip_id).get();
    }

    public Collection<Trip> history(Integer userId) {
        return getAllTrips().stream()
                .filter(t -> isParticipant(t.getParticipants(), userId))
                .collect(Collectors.toList());
    }

    private boolean isParticipant(Collection<TripParticipant> participants, Integer userId) {
        return participants.stream().anyMatch(p -> userId.equals(p.getUserId()));
    }
}
