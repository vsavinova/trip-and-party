package com.evolve.server.service;

import com.evolve.model.Guide;
import com.evolve.server.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
}
