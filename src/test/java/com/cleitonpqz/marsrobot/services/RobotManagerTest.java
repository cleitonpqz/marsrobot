package com.cleitonpqz.marsrobot.services;

import com.cleitonpqz.marsrobot.entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RobotManagerTest {

    @Autowired
    private RobotManager service;

    LandManager landManager;

    @BeforeAll
    public void setUp() throws Exception {
        //GIVEN
        landManager = (x, y) -> new Land(0, --x, 0, --y);
    }

    @Test
    void start() {
        //GIVEN
        Robot robot;

        //WHEN
        robot = service.start();

        //THEN
        assertAll("attributes",
                () -> {
                    Face face = robot.getFaceEnum();
                    assertEquals(Face.NORTH.getFace(), face.getFace());
                    assertEquals(Face.NORTH, face);
                },
                () -> {
                    Coordinate c = robot.getCoordinate();
                    assertEquals(0, c.getX());
                    assertEquals(0, c.getY());
                },
                () -> assertNull(robot.getMovements())
        );
    }

    @Test
    void registerMovements() {
        //GIVEN
        Robot robot = service.start();
        char[] movements = "MML".toCharArray();

        //WHEN
        service.registerMovements(robot, movements);

        //THEN
        assertEquals(movements, robot.getMovements());
    }

    @Test
    void move() throws Exception {
        //GIVEN
        Robot robot = service.start();
        Land land = landManager.create(5, 5);

        //WHEN
        service.move(robot, land);

        //THEN
        assertEquals(1, robot.getCoordinate().getY());

    }

    @Test
    void rotate() {
        //GIVEN
        Robot robot = service.start();

        //AND
        assertEquals(Face.NORTH, robot.getFaceEnum());

        //WHEN
        service.rotate(robot, Direction.LEFT);

        //THEN
        assertEquals(Face.WEST, robot.getFaceEnum());

    }

    @Test
    void getFinalPosition() throws Exception {
        //GIVEN
        Robot robot = service.start();
        Land land = landManager.create(5, 5);
        char[] movements = "MML".toCharArray();
        service.registerMovements(robot, movements);

        //WHEN
        service.getFinalPosition(robot, land);

        //THEN
        assertAll("position",
                () -> {
                    Coordinate coordinate = robot.getCoordinate();
                    assertEquals(0, coordinate.getX());
                    assertEquals(2, coordinate.getY());
                },
                () -> assertEquals(Face.WEST, robot.getFaceEnum())
        );
    }

    @Test
    void getFinalPositionWithException() throws Exception {
        //GIVEN
        Robot robot = service.start();
        Land land = landManager.create(5, 5);
        char[] movements = "MMMMMM".toCharArray();
        service.registerMovements(robot, movements);

        //WHEN
        Throwable exception = assertThrows(Exception.class, () -> service.getFinalPosition(robot, land));

        //THEN
        assertEquals(Exception.class, exception.getClass());
    }

}