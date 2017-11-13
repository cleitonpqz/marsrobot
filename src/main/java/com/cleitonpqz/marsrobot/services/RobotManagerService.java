package com.cleitonpqz.marsrobot.services;

import com.cleitonpqz.marsrobot.entities.*;
import org.springframework.stereotype.Service;

import static com.cleitonpqz.marsrobot.entities.Face.*;

@Service
public class RobotManagerService implements RobotManager {

    @Override
    public Robot start() {
        return new Robot(new Coordinate(0, 0), NORTH);
    }

    @Override
    public void registerMovements(Robot robot, char[] movements) {
        robot.setMovements(movements);
    }

    @Override
    public void move(Robot robot, Land land) throws Exception {
        switch (robot.getFaceEnum()) {
            case NORTH:
                moveNorth(robot, land);
                break;
            case SOUTH:
                moveSouth(robot, land);
                break;
            case WEST:
                moveWest(robot, land);
                break;
            case EAST:
                moveEast(robot, land);
                break;
        }
    }

    @Override
    public void rotate(Robot robot, Direction direction) {
        switch (direction) {
            case LEFT:
                rotateLeft(robot);
                break;
            case RIGHT:
                rotateRight(robot);
                break;
        }
    }

    @Override
    public Robot getFinalPosition(Robot robot, Land land) throws Exception {
        for(char movement : robot.getMovements()) {
            switch (movement) {
                case 'M':
                    move(robot, land);
                    break;
                case 'L':
                    rotate(robot, Direction.LEFT);
                    break;
                case 'R':
                    rotate(robot, Direction.RIGHT);
                    break;
                default:
                    throw new Exception();
            }
        }
        return robot;
    }

    private void moveEast(Robot robot, Land land) throws Exception {
        robot.getCoordinate().setX(robot.getCoordinate().getX() + 1);
        if(robot.getCoordinate().getX() > land.getEndX()) {
            throw new Exception();
        }
    }

    private void moveWest(Robot robot, Land land) throws Exception {
        robot.getCoordinate().setX(robot.getCoordinate().getX() - 1);
        if(robot.getCoordinate().getX() < land.getStartX()) {
            throw new Exception();
        }
    }

    private void moveSouth(Robot robot, Land land) throws Exception {
        robot.getCoordinate().setY(robot.getCoordinate().getY() - 1);
        if(robot.getCoordinate().getY() < land.getStartY()) {
            throw new Exception();
        }
    }

    private void moveNorth(Robot robot, Land land) throws Exception {
        robot.getCoordinate().setY(robot.getCoordinate().getY() + 1);
        if(robot.getCoordinate().getY() > land.getEndY()) {
            throw new Exception();
        }
    }

    private void rotateRight(Robot robot) {
        switch (robot.getFaceEnum()) {
            case NORTH:
                robot.setFaceEnum(EAST);
                break;
            case SOUTH:
                robot.setFaceEnum(WEST);
                break;
            case EAST:
                robot.setFaceEnum(SOUTH);
                break;
            case WEST:
                robot.setFaceEnum(NORTH);
                break;
        }
    }

    private void rotateLeft(Robot robot) {
        switch (robot.getFaceEnum()) {
            case NORTH:
                robot.setFaceEnum(WEST);
                break;
            case SOUTH:
                robot.setFaceEnum(EAST);
                break;
            case EAST:
                robot.setFaceEnum(NORTH);
                break;
            case WEST:
                robot.setFaceEnum(SOUTH);
                break;
        }
    }

}
