package com.evolve.model;

public enum Visibility {
    ONE_HAND_FRIEND, ALL;

    public static Visibility of(int type) {
        switch (type) {
            case 0: return ONE_HAND_FRIEND;
            default: return ALL;
        }
    }
}
