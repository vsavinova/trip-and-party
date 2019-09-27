package com.evolve.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
public class UserSettings {
    private Anonimity anonimity;

    public UserSettings(Anonimity anonimity) {
        this.anonimity = anonimity;
    }

    public UserSettings() {
    }
}