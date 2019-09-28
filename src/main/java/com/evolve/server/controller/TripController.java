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

//    @GetMapping(value = "/create")
//    public Response createTrip(@RequestParam(name = "org_id") Integer orgId,
//                               @RequestParam(name = "guide_id") Integer guideId, @RequestParam(name = "visibility") Visibility visibility,
//                               @RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                               @RequestParam(name = "finish_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate,
//                               @RequestParam(name = "chat") String chat) {
//        try {
////            return new Response(OK_RESPONSE, tripService.createTrip(orgId, guideId, visibility, startDate, finishDate, chat));
//        } catch (Throwable e) {
//            return new Response(ERROR_RESPONSE, e.getMessage());
//        }
//    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Response create(@RequestBody Trip trip) {
        try {
            return new Response(OK_RESPONSE, tripService.createTrip(trip));
        } catch (Throwable e) {
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
