package com.cleitonpqz.marsrobot.entities;

public enum Face {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    Face(String face) {
        this.face = face;
    }

    private String face;
    public String getFace() {
        return face;
    }
}
