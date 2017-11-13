package com.cleitonpqz.marsrobot.services;

import com.cleitonpqz.marsrobot.entities.Land;
import org.springframework.stereotype.Service;

@Service
public class LandManagerService implements LandManager {

    @Override
    public Land create(int xSize, int ySize) {
        return new Land(0, --xSize, 0, --ySize);
    }
}
