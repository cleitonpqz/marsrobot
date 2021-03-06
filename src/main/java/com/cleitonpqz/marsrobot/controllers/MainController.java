package com.cleitonpqz.marsrobot.controllers;

import com.cleitonpqz.marsrobot.entities.Land;
import com.cleitonpqz.marsrobot.entities.Robot;
import com.cleitonpqz.marsrobot.services.LandManager;
import com.cleitonpqz.marsrobot.services.RobotManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/rest")
public class MainController {

    private RobotManager robotManager;

    @Autowired
    public MainController(RobotManager robotManager) {
        this.robotManager = robotManager;
    }

    @PostMapping("/mars/{movements}")
    public Robot movement(@PathVariable("movements") char[] movements) {
        LandManager lm = (x, y) -> new Land(0, --x, 0, --y);
        Land land = lm.create(5, 5);
        Robot robot = robotManager.start();
        robotManager.registerMovements(robot, movements);
        try {
            return robotManager.getFinalPosition(robot, land);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}

