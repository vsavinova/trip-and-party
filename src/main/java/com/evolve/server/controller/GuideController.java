package com.evolve.server.controller;

import com.evolve.server.common.Response;
import com.evolve.server.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@RequestMapping("/guide")
public class GuideController {
    @Autowired
    GuideService guideService;

    @GetMapping(value = "/get_all")
    public Response getAllTrips(@RequestParam(name = "city", required = false) String city) {
        try {
            return new Response(OK_RESPONSE, guideService.getGuides(city));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }
}
