package com.evolve.server.controller;

import com.evolve.model.AcceptStatus;
import com.evolve.model.Trip;
import com.evolve.model.Visibility;
import com.evolve.server.common.Response;
import com.evolve.server.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@CrossOrigin
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping(value = "/hello", produces="application/json")
    public Response hello() {
        try {
            return new Response(OK_RESPONSE, "hello!!!!");
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }

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

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public Response update(@RequestBody Trip trip) {
        try {
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

    @GetMapping(value = "/get")
    public Response getAllTrips(@RequestParam(name = "user_id") Integer userId,
                                @RequestParam(name = "city", required = false) String city,
                                @RequestParam(name = "visibility", required = false) Visibility visibility,
                                @RequestParam(name = "startDate", required = false, defaultValue = "1900-01-01") LocalDate startDate,
                                @RequestParam(name = "finishDate", required = false, defaultValue = "3000-01-01") LocalDate finishDate) {
        try {
            return new Response(OK_RESPONSE, tripService.getTrips(userId, city, visibility, startDate, finishDate));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
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
