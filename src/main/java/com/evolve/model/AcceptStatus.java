package com.evolve.model;

public enum AcceptStatus {
    DECLINED, ACCEPTED, UNDEFINED;

    public static AcceptStatus of(int type) {
        switch (type) {
            case 0: return DECLINED;
            case 1: return ACCEPTED;
            default: return UNDEFINED;
        }
    }
}
