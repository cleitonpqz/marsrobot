package com.cleitonpqz.marsrobot.entities;

public enum Direction {
    LEFT("L"),
    RIGHT("R");


    Direction(String direction) {
        this.direction = direction;
    }

    private String direction;

    public String getDirection() {
        return direction;
    }


}
