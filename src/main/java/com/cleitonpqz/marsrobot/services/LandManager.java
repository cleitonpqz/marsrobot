package com.cleitonpqz.marsrobot.services;

import com.cleitonpqz.marsrobot.entities.Land;

@FunctionalInterface
public interface LandManager {
    Land create(int xSize, int ySize);
}
