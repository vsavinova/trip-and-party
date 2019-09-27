package com.evolve.server.controller;

import com.evolve.server.common.Response;
import com.evolve.server.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping(value = "/get_all")
    public Response getAllTrips() {
        try {
            return new Response(OK_RESPONSE, tripService.getAllTrips());
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }
}
