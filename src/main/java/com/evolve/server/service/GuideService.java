package com.evolve.server.service;

import com.evolve.model.Guide;
import com.evolve.model.HashTag;
import com.evolve.server.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideService {
    @Autowired
    GuideRepository guideRepository;

    public Collection<Guide> getGuides() {
        return guideRepository.findAll();
    }

    public Guide createGuide(Guide guide) {
        return guideRepository.save(guide);
    }

    public Guide getGuide(int guide_id) {
        return guideRepository.findById(guide_id).get();
    }

    public Guide updateLikes(Integer guideId, Boolean like) {
        Guide guide = guideRepository.findById(guideId).get();
        guide.setLikes(like ? guide.getLikes() + 1 : guide.getLikes() - 1);
        guideRepository.save(guide);
        return guide;
    }

    public Collection<Guide> getGuides(String city, Collection<String> hashtags, String budget) {
        Collection<Guide> guides = guideRepository.getAll(city);
        return filterGuides(guides, hashtags, budget);
    }

    private Collection<Guide> filterGuides(Collection<Guide> guides, Collection<String> hashtags, String budget) {
        return guides.stream()
                .filter(g ->
                {
                    List<String> guideHashtags = g.getHashtags()
                            .stream()
                            .map(HashTag::getName)
                            .collect(Collectors.toList());
                    return (hashtags.isEmpty()
                            || !(new ArrayList<>(hashtags)
                            .retainAll(guideHashtags)))
                            && compareBudget(g.getBudget(), budget);
                })
                .collect(Collectors.toList());

    }

    public static boolean compareBudget(String guideBudget, String budget) {
        return (guideBudget == null || budget == null) ||
                (guideBudget.isEmpty()) || (budget.isEmpty()) ||
                (Integer.parseInt(guideBudget) <= Integer.parseInt(budget));
    }
}
