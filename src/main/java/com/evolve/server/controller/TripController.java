package com.evolve.server.controller;

import com.evolve.model.Trip;
import com.evolve.model.Visibility;
import com.evolve.server.common.Response;
import com.evolve.server.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping(value = "/get_all")
    public Response getAllTrips() {
        try {
            return new Response(OK_RESPONSE, tripService.getAllTrips());
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }
}
