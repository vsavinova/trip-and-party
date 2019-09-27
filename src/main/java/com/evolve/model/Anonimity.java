package com.evolve.model;

public enum Anonimity {
    ALL_VISIBLE(1), TRIPS_VISIBLE(2), EVENTS_VISIBLE(3), ALL_NONVISIBLE(4);

    private int type;

    Anonimity(int type) {
        this.type = type;
    }
}