package com.evolve.server.controller;

import com.evolve.model.AcceptStatus;
import com.evolve.model.Trip;
import com.evolve.model.Visibility;
import com.evolve.server.common.Response;
import com.evolve.server.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@CrossOrigin
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping(value = "/hello", produces = "application/json")
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

//    @GetMapping(value = "/get")
//    public Response getAllTrips(@RequestParam(name = "user_id") Integer userId,
//                                @RequestParam(name = "city", required = false) String city,
//                                @RequestParam(name = "visibility", required = false) Visibility visibility,
//                                @RequestParam(name = "startDate", required = false, defaultValue = "1900-01-01") LocalDate startDate,
//                                @RequestParam(name = "finishDate", required = false, defaultValue = "3000-01-01") LocalDate finishDate) {
//        try {
//            return new Response(OK_RESPONSE, tripService.getTrips(userId, city, visibility, startDate, finishDate));
//        } catch (Throwable e) {
//            return new Response(ERROR_RESPONSE, e.getMessage());
//        }
//    }

    @PostMapping(value = "/get", consumes = "application/json", produces = "application/json")
    public Response get(@RequestBody Map<String, Object> params) {
        try {
            Boolean mine = (Boolean) params.get("mine");
            Integer user_id = (Integer) params.get("user_id");
            if ((mine != null) && mine)
                return new Response(OK_RESPONSE, tripService.history(user_id));

            Integer trip_id = params.get("trip_id") != null ? (Integer) params.get("trip_id") : null;
            if (trip_id != null)
                return new Response(OK_RESPONSE, Collections.singletonList(tripService.getTrip(trip_id)));

            String city = params.get("city") != null ? ((String) params.get("city")) : "";
            String budget = params.get("budget") != null ? (String) params.get("budget") : null;
            Visibility visibility = params.get("visibility") != null ? Visibility.of((Integer) params.get("visibility")) : Visibility.ALL;
            LocalDate startDate = params.get("start_date") != null ? LocalDate.parse((String) params.get("start_date")) :  LocalDate.parse("1900-01-01");
            LocalDate finishDate = params.get("finish_date") != null ? LocalDate.parse((String) params.get("finish_date")) : LocalDate.parse("3000-01-01");
            Collection<Map<String, Object>> hashtagsMapList = (Collection<Map<String, Object>>) params.get("hashtags");
            List<String> hashtags = null;
            if (hashtagsMapList != null) {
                hashtags = hashtagsMapList.stream()
                        .map(m -> (String) m.get("name"))
                        .collect(Collectors.toList());
            }
            return new Response(OK_RESPONSE, tripService.getTrips(user_id, visibility, startDate,
                    finishDate, city, hashtags, budget));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e);
        }
    }

    @GetMapping(value = "/history")
    public Response history(Integer userId) {
        try {
            return new Response(OK_RESPONSE, tripService.history(userId));
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
