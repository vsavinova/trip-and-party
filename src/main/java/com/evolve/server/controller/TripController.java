package com.evolve.server.controller;

import com.evolve.model.AcceptStatus;
import com.evolve.model.Trip;
import com.evolve.server.common.Response;
import com.evolve.server.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Response create(@RequestBody Trip trip) {
        try {
            System.out.println(trip.toString());
            System.out.println(trip.getGuide());
            System.out.println(trip.getGuide().getGuide_id());
            System.out.println(trip.getGuide().getCreator_id());
            return new Response(OK_RESPONSE, tripService.createTrip(trip));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e);
        }
    }

    @GetMapping(value = "/apply")
    public Response apply(@RequestParam(name = "trip_id") Integer tripId,
                          @RequestParam(name = "user_id") Integer vkId) {
        try {
            return new Response(OK_RESPONSE, tripService.apply(tripId, vkId));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }

    @GetMapping(value = "/respond")
    public Response respondOnUserRequest(@RequestParam(name = "participant_id") Integer participant_id,
                                         @RequestParam(name = "org_id") Integer orgId,
                                         @RequestParam(name = "response") AcceptStatus response) {
        try {
            return new Response(OK_RESPONSE, tripService.respondOnUserRequest(participant_id, orgId, response));
        } catch (Throwable e) {
            e.printStackTrace();
            return new Response(ERROR_RESPONSE, e);
        }
    }


    @GetMapping(value = "/get_all")
    public Response getAllTrips() {
        try {
            return new Response(OK_RESPONSE, tripService.getAllTrips());
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }
}
