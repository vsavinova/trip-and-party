package com.evolve.server.controller;

import com.evolve.model.Guide;
import com.evolve.server.common.Response;
import com.evolve.server.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.evolve.server.common.Constants.ERROR_RESPONSE;
import static com.evolve.server.common.Constants.OK_RESPONSE;

@RestController
@CrossOrigin
@RequestMapping("/guide")
public class GuideController {
    @Autowired
    GuideService guideService;

    @GetMapping(value = "/like")
    public Response updateLikes(@RequestParam(name = "guide_id") Integer guideId,
                                @RequestParam(name = "like") Boolean like) {
        return new Response(OK_RESPONSE, guideService.updateLikes(guideId, like));
    }

    @GetMapping(value = "/get_all")
    public Response getAllGuides(@RequestParam(name = "city", required = false) String city) {
        try {
            return new Response(OK_RESPONSE, guideService.getGuides());
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e.getMessage());
        }
    }

    @PostMapping(value = "/get", consumes = "application/json", produces = "application/json")
    public Response get(@RequestBody Map<String, Object> params) {
        try {
            Integer guide_id = (Integer) params.get("guide_id");
            if (guide_id != null)
                return new Response(OK_RESPONSE, Collections.singletonList(guideService.getGuide(guide_id)));

            String city = (String) params.get("city");
            String budget = (String) params.get("budget");
            Collection<Map<String, Object>> hashtagsMapList = (Collection<Map<String, Object>>) params.get("hashtags");
            List<String> hashtags = null;
            if (hashtagsMapList != null) {
                hashtags = hashtagsMapList.stream()
                        .map(m -> (String) m.get("name"))
                        .collect(Collectors.toList());
            }
            return new Response(OK_RESPONSE, guideService.getGuides(city, hashtags, budget));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e);
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

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public Response update(@RequestBody Guide guide) {
        try {
            return new Response(OK_RESPONSE, guideService.createGuide(guide));
        } catch (Throwable e) {
            return new Response(ERROR_RESPONSE, e);
        }
    }
}
