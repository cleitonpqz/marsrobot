package com.cleitonpqz.marsrobot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Robot {
    private Coordinate coordinate;

    @JsonIgnore
    private Face faceEnum;

    @JsonIgnore
    private char[] movements;

    private String face;


    public Robot(Coordinate coordinate, Face faceEnum) {
        this.coordinate = coordinate;
        this.faceEnum = faceEnum;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Face getFaceEnum() {
        return faceEnum;
    }

    public void setFaceEnum(Face faceEnum) {
        this.faceEnum = faceEnum;
    }

    public char[] getMovements() {
        return movements;
    }

    public void setMovements(char[] movements) {
        this.movements = movements;
    }

    public String getFace() {
        return faceEnum.getFace();
    }

    public void setFace(String face) {
        this.face = face;
    }
}
