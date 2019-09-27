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

    public Collection<Guide> getGuides(String city) {
        return guideRepository.findAll(city);
    }
}
