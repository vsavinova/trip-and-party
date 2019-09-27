package com.evolve.server.controller;

import com.evolve.model.Guide;
import com.evolve.server.common.Response;
import com.evolve.server.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@RequestMapping("/guide")
public class GuideController {
    @Autowired
    GuideService guideService;

    @GetMapping(value = "/get_all")
    public Response getAllGuides(@RequestParam(name = "city", required = false) String city) {
        try {
            return new Response(OK_RESPONSE, guideService.getGuides());
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Response create(@RequestBody Guide guide) {
        try {
            return new Response(OK_RESPONSE, guideService.createGuide(guide));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e);
        }
    }
}
