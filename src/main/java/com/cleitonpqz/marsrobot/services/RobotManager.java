package com.cleitonpqz.marsrobot.services;

import com.cleitonpqz.marsrobot.entities.Direction;
import com.cleitonpqz.marsrobot.entities.Land;
import com.cleitonpqz.marsrobot.entities.Robot;

public interface RobotManager {
    Robot start();
    void registerMovements(Robot robot, char[] movements);
    void move(Robot robot, Land land) throws Exception;
    void rotate(Robot robot, Direction direction);
    Robot getFinalPosition(Robot robot, Land land) throws Exception;
}
